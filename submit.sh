#!/usr/bin/env bash
set -euo pipefail

# --- Colors ---
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[0;33m'
CYAN='\033[0;36m'
BOLD='\033[1m'
DIM='\033[2m'
RESET='\033[0m'

info()  { echo -e "${CYAN}[INFO]${RESET}  $*"; }
ok()    { echo -e "${GREEN}[OK]${RESET}    $*"; }
warn()  { echo -e "${YELLOW}[WARN]${RESET}  $*"; }
fail()  { echo -e "${RED}[FAIL]${RESET}  $*"; exit 1; }
step()  { echo -e "\n${BOLD}--- $* ---${RESET}"; }

WORKSPACE_DIR="$(cd "$(dirname "$0")" && pwd)"
MEMBERS_FILE="$WORKSPACE_DIR/co-authers.yaml"

# --- Select project ---
if [[ -n "${1:-}" ]]; then
    PROJECT_DIR="$1"
else
    PROJECT_DIR=$(ls -d "$WORKSPACE_DIR"/Ex*_project/ 2>/dev/null | sed 's|/$||' | xargs -n1 basename | fzf --prompt='Select project to submit> ')
fi

[[ -z "$PROJECT_DIR" ]] && fail "No project selected."

PROJECT_PATH="$WORKSPACE_DIR/$PROJECT_DIR"

[[ ! -f "$PROJECT_PATH/pom.xml" ]] && fail "No pom.xml found in $PROJECT_DIR"

# --- Read last names from YAML ---
[[ ! -f "$MEMBERS_FILE" ]] && fail "Members file not found. Please fill in co-authers.yaml first."

LASTNAMES=$(yq -r '.members[].lastname' "$MEMBERS_FILE" | paste -sd '')

[[ -z "$LASTNAMES" || "$LASTNAMES" == "null" ]] && fail "No last names found in co-authers.yaml. Please fill it in."

# --- Extract exercise number from folder name (e.g. Ex00) ---
EX_NUMBER=$(echo "$PROJECT_DIR" | grep -oP '^Ex\d+')

ZIP_NAME="${EX_NUMBER}_${LASTNAMES}_project"
SUBMIT_DIR="$WORKSPACE_DIR/submissions"
mkdir -p "$SUBMIT_DIR"
ZIP_FILE="$SUBMIT_DIR/$ZIP_NAME.zip"

echo ""
echo -e "${BOLD}========================================${RESET}"
echo -e "  ${CYAN}Project${RESET}:  $PROJECT_DIR"
echo -e "  ${CYAN}Members${RESET}:  $LASTNAMES"
echo -e "  ${CYAN}Output${RESET}:   ${YELLOW}$ZIP_NAME.zip${RESET}"
echo -e "${BOLD}========================================${RESET}"

# --- Compile ---
step "Compile"
info "Running mvn compile..."
cd "$PROJECT_PATH"
if mvn compile -q; then
    ok "Compilation successful."
else
    fail "Compilation failed. Fix errors before submitting."
fi

# --- Test ---
step "Test"
info "Running mvn test..."
if mvn test -q; then
    ok "All tests passed."
else
    fail "Tests failed. Fix failing tests before submitting."
fi

# --- Clean build artifacts before zipping ---
step "Clean"
info "Removing build artifacts..."
mvn clean -q
ok "Clean complete."

# --- Zip ---
step "Package"
info "Creating $ZIP_NAME.zip ..."
cd "$WORKSPACE_DIR"
rm -f "$ZIP_FILE"

cp -r "$PROJECT_DIR" "$ZIP_NAME"
zip -rq "$ZIP_FILE" "$ZIP_NAME" -x "$ZIP_NAME/target/*" "$ZIP_NAME/.idea/*" "$ZIP_NAME/*.iml"
rm -rf "$ZIP_NAME"

ok "ZIP created."

# --- Summary ---
echo ""
echo -e "${BOLD}========================================${RESET}"
echo -e "  ${GREEN}Submission ready!${RESET}"
echo -e "  ${DIM}$ZIP_FILE${RESET}"
echo -e ""
echo -e "  ${DIM}Prof can unzip and run:${RESET}"
echo -e "  ${CYAN}mvn compile && mvn test${RESET}"
echo -e "${BOLD}========================================${RESET}"
echo ""
