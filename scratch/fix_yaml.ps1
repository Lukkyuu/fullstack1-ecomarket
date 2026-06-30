$ymls = Get-ChildItem -Path . -Filter "application.yml" -Recurse
foreach ($yml in $ymls) {
    $content = Get-Content $yml.FullName
    $newContent = ""
    foreach ($line in $content) {
        if ($line -match '^logging\.pattern\.console:\s*(.*)') {
            $val = $matches[1]
            if (-not $val.StartsWith('"')) {
                $newContent += "logging.pattern.console: `"$val`"`n"
            } else {
                $newContent += "$line`n"
            }
        } else {
            $newContent += "$line`n"
        }
    }
    Set-Content -Path $yml.FullName -Value $newContent
}
