#!/usr/bin/env sh
set -eu

OUT_FILE="exp10_file_contents.txt"
EXP_DIR="app/src/main/java/yash/c197/experiments/exp10_room_database"

if [ ! -d "$EXP_DIR" ]; then
  printf 'Exp 10 folder not found: %s\n' "$EXP_DIR" >&2
  exit 1
fi

: > "$OUT_FILE"

printf 'EXP 10 ROOM DATABASE SOURCE FILES\n' >> "$OUT_FILE"
printf 'Generated from: %s\n' "$EXP_DIR" >> "$OUT_FILE"
printf '%s\n\n' '============================================================' >> "$OUT_FILE"

find "$EXP_DIR" -type f | sort | while IFS= read -r file; do
  printf '%s\n' '============================================================' >> "$OUT_FILE"
  printf 'FILE: %s\n' "$file" >> "$OUT_FILE"
  printf '%s\n' '============================================================' >> "$OUT_FILE"
  cat "$file" >> "$OUT_FILE"
  printf '\n\n' >> "$OUT_FILE"
done

printf 'Created %s\n' "$OUT_FILE"
