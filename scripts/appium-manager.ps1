param(
    [Parameter(Mandatory=$true)]
    [ValidateSet("start", "stop", "status", "restart")]
    [string]$Action
)

function Start-AppiumServer {
    Write-Host "Starting Appium Server..."
    
    $existing = Get-Process | Where-Object {$_.ProcessName -eq "node" -and $_.CommandLine -like "*appium*"}
    if ($existing) {
        Write-Host "Appium server already running on PID: $($existing.Id)"
        return
    }
    
    Start-Process -WindowStyle Normal -FilePath "appium" -ArgumentList "--address", "127.0.0.1", "--port", "4723"
    Start-Sleep -Seconds 3
    
    $running = netstat -an | findstr "4723"
    if ($running) {
        Write-Host "Appium server started on http://127.0.0.1:4723"
    } else {
        Write-Host "Failed to start Appium server"
    }
}

function Stop-AppiumServer {
    Write-Host "Stopping Appium Server..."
    
    $processes = Get-Process | Where-Object {$_.ProcessName -eq "node"}
    if ($processes) {
        $processes | Stop-Process -Force
        Write-Host "Appium server stopped"
    } else {
        Write-Host "No Appium server processes found"
    }
}

function Get-AppiumStatus {
    Write-Host "Checking Appium Server Status..."
    
    $portCheck = netstat -an | findstr "4723"
    if ($portCheck) {
        Write-Host "Appium server is running on port 4723"
        Write-Host "Server URL: http://127.0.0.1:4723"
    } else {
        Write-Host "Appium server is not running"
    }
    
    $nodeProcesses = Get-Process | Where-Object {$_.ProcessName -eq "node"}
    if ($nodeProcesses) {
        Write-Host "Node.js processes:"
        $nodeProcesses | ForEach-Object {
            Write-Host "  PID: $($_.Id) | Memory: $([math]::Round($_.WorkingSet64/1MB, 2))MB"
        }
    }
    
    Write-Host "Connected Android Devices:"
    try {
        $devices = adb devices 2>$null
        if ($devices) {
            Write-Host $devices
        } else {
            Write-Host "  ADB not found or no devices connected"
        }
    } catch {
        Write-Host "  ADB not available"
    }
}

switch ($Action) {
    "start" { Start-AppiumServer }
    "stop" { Stop-AppiumServer }
    "status" { Get-AppiumStatus }
    "restart" { 
        Stop-AppiumServer
        Start-Sleep -Seconds 2
        Start-AppiumServer
    }
}