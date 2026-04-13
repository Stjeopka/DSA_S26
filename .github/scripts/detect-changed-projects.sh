#!/usr/bin/env bash
# detect-changed-projects.sh
#
# Compares two git SHAs and outputs a JSON array of project directory names
# (matching the pattern Ex*_project/) that contain changed, non-documentation files.
#
# Usage:
#   detect-changed-projects.sh <base_sha> <head_sha>
#
# Output (stdout):
#   A JSON array, e.g.  ["Ex00_LastNamesOfMembers_project"]
#   or an empty array   []  when nothing relevant changed.
#
# "Relevant" means the file is:
#   1. Inside a top-level Ex*_project/ directory
#   2. NOT a documentation-only file (.md, .txt, .rst, README*)

set -euo pipefail

BASE="${1:?Usage: $0 <base_sha> <head_sha>}"
HEAD="${2:?Usage: $0 <base_sha> <head_sha>}"

# Guard: base SHA is the "empty tree" sentinel emitted when there is no
# previous commit (e.g. first push to a branch). Fall back to comparing
# HEAD^ so we still get a sensible diff.
if [[ "$BASE" == "0000000000000000000000000000000000000000" ]]; then
  BASE=$(git rev-parse HEAD^ 2>/dev/null || git rev-list --max-parents=0 HEAD)
fi

# Collect changed files between the two commits.
CHANGED_FILES=$(git diff --name-only "$BASE" "$HEAD" 2>/dev/null || true)

# Build a deduplicated list of relevant project directories.
declare -A SEEN
PROJECTS=()

while IFS= read -r file; do
  # Must be inside a top-level Ex*_project/ directory.
  if [[ "$file" =~ ^(Ex[^/]+_project)/ ]]; then
    PROJECT="${BASH_REMATCH[1]}"

    # Skip documentation-only files — they do not affect build or tests.
    if [[ "$file" =~ \.(md|txt|rst)$ ]] || [[ "$file" =~ /README([^/]*)$ ]]; then
      continue
    fi

    # Deduplicate: add each project at most once.
    if [[ -z "${SEEN[$PROJECT]:-}" ]]; then
      PROJECTS+=("\"$PROJECT\"")
      SEEN[$PROJECT]=1
    fi
  fi
done <<< "$CHANGED_FILES"

# Emit a valid JSON array.
if [[ ${#PROJECTS[@]} -eq 0 ]]; then
  echo "[]"
else
  # Join with commas: ["Ex00_...","Ex01_..."]
  printf '['; printf '%s,' "${PROJECTS[@]}" | sed 's/,$//'; printf ']'
  echo
fi
