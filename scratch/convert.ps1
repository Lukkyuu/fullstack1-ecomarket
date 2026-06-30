$props = Get-ChildItem -Path . -Filter "application.properties" -Recurse
foreach ($prop in $props) {
    $ymlFile = Join-Path -Path $prop.DirectoryName -ChildPath "application.yml"
    $lines = Get-Content -Path $prop.FullName
    $yamlContent = ""
    foreach ($line in $lines) {
        $trimmed = $line.Trim()
        if ($trimmed.StartsWith("#") -or $trimmed -eq "") {
            $yamlContent += "$trimmed`n"
            continue
        }
        $parts = $trimmed -split '=', 2
        if ($parts.Length -eq 2) {
            $key = $parts[0].Trim()
            $value = $parts[1].Trim()
            $yamlContent += "$($key): $($value)`n"
        }
    }
    Set-Content -Path $ymlFile -Value $yamlContent
    Remove-Item -Path $prop.FullName
}
