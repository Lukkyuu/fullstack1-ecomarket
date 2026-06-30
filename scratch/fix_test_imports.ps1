$tests = Get-ChildItem -Path . -Filter *ControllerTest.java -Recurse | Where-Object { $_.FullName -notmatch 'auth-service' -and $_.FullName -notmatch 'order-service' }
foreach ($test in $tests) {
    $content = Get-Content $test.FullName
    $newContent = ""
    $pkg = ""
    foreach ($line in $content) {
        $newContent += "$line`n"
        if ($line -match '^package (.*);') {
            $pkg = $matches[1]
            $repoPkg = $pkg -replace '\.controller', '.repository'
            $newContent += "import ${repoPkg}.*;`n"
        }
    }
    Set-Content -Path $test.FullName -Value $newContent
}
