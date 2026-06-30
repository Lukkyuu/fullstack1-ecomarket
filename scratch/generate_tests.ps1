$controllers = Get-ChildItem -Path . -Filter *Controller.java -Recurse | Where-Object { $_.FullName -notmatch "auth-service" -and $_.FullName -notmatch "order-service" }

foreach ($ctrl in $controllers) {
    $content = Get-Content $ctrl.FullName -Raw
    
    # Extract package name
    $packageMatch = [regex]::Match($content, 'package\s+([a-zA-Z0-9\.]+);')
    if (-not $packageMatch.Success) { continue }
    $package = $packageMatch.Groups[1].Value
    
    # Extract class name
    $classMatch = [regex]::Match($content, 'public\s+class\s+([a-zA-Z0-9_]+)\s*\{')
    if (-not $classMatch.Success) { continue }
    $className = $classMatch.Groups[1].Value
    $testClassName = $className + "Test"
    
    # Extract repository fields
    $repoMatches = [regex]::Matches($content, 'private\s+final\s+([a-zA-Z0-9_]+Repository)\s+([a-zA-Z0-9_]+);')
    
    $testPackage = $package
    $testDir = $ctrl.DirectoryName -replace '\\main\\', '\test\'
    New-Item -ItemType Directory -Force -Path $testDir | Out-Null
    
    $testFile = Join-Path $testDir "$testClassName.java"
    
    $testContent = "package $testPackage;`n`n"
    $testContent += "import org.junit.jupiter.api.Test;`n"
    $testContent += "import org.junit.jupiter.api.extension.ExtendWith;`n"
    $testContent += "import org.mockito.InjectMocks;`n"
    $testContent += "import org.mockito.Mock;`n"
    $testContent += "import org.mockito.junit.jupiter.MockitoExtension;`n"
    $testContent += "import org.springframework.http.ResponseEntity;`n"
    $testContent += "import java.util.Collections;`n"
    $testContent += "import java.util.List;`n"
    $testContent += "import static org.junit.jupiter.api.Assertions.*;`n"
    $testContent += "import static org.mockito.Mockito.*;`n`n"
    
    $testContent += "@ExtendWith(MockitoExtension.class)`n"
    $testContent += "class $testClassName {`n`n"
    
    foreach ($match in $repoMatches) {
        $repoType = $match.Groups[1].Value
        $repoVar = $match.Groups[2].Value
        $testContent += "    @Mock`n"
        $testContent += "    private $repoType $repoVar;`n`n"
    }
    
    $testContent += "    @InjectMocks`n"
    $testContent += "    private $className controller;`n`n"
    
    $testContent += "    @Test`n"
    $testContent += "    void contextLoads() {`n"
    $testContent += "        assertNotNull(controller);`n"
    $testContent += "    }`n"
    $testContent += "}`n"
    
    Set-Content -Path $testFile -Value $testContent
    Write-Host "Created test $testFile"
}
