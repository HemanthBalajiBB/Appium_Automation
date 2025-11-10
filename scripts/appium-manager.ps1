#!/usr/bin/env pwsh
# Appium Server Management Script

param(
    [Parameter(Mandatory=$true)]
    [ValidateSet("start", "stop", "status", "restart")]
    [string]$Action
)

function Start-AppiumServer {
    Write-Host "🚀 Starting Appium Server..." -ForegroundColor Green
    
    # Check if already running
    $existing = Get-Process | Where-Object {$_.ProcessName -eq "node" -and $_.CommandLine -like "*appium*"}
    if ($existing) {
        Write-Host "⚠️  Appium server is already running on PID: $($existing.Id)" -ForegroundColor Yellow
        return
    }
    
    # Start Appium server
    Start-Process -WindowStyle Normal -FilePath "appium" -ArgumentList "--address", "127.0.0.1", "--port", "4723"
    Start-Sleep -Seconds 3
    
    # Verify it started
    $running = netstat -an | findstr "4723"
    if ($running) {
        Write-Host "✅ Appium server started successfully on http://127.0.0.1:4723" -ForegroundColor Green
    } else {
        Write-Host "❌ Failed to start Appium server" -ForegroundColor Red
    }
}

function Stop-AppiumServer {
    Write-Host "🛑 Stopping Appium Server..." -ForegroundColor Yellow
    
    $processes = Get-Process | Where-Object {$_.ProcessName -eq "node"}
    if ($processes) {
        $processes | Stop-Process -Force
        Write-Host "✅ Appium server stopped" -ForegroundColor Green
    } else {
        Write-Host "ℹ️  No Appium server processes found" -ForegroundColor Blue
    }
}

function Get-AppiumStatus {
    Write-Host "📊 Checking Appium Server Status..." -ForegroundColor Blue
    
    # Check if port is listening
    $portCheck = netstat -an | findstr "4723"
    if ($portCheck) {
        Write-Host "✅ Appium server is RUNNING on port 4723" -ForegroundColor Green
        Write-Host "   Server URL: http://127.0.0.1:4723" -ForegroundColor Gray
    } else {
        Write-Host "❌ Appium server is NOT running" -ForegroundColor Red
    }
    
    # Check for node processes
    $nodeProcesses = Get-Process | Where-Object {$_.ProcessName -eq "node"}
    if ($nodeProcesses) {
        Write-Host "📋 Node.js processes:" -ForegroundColor Blue
        $nodeProcesses | ForEach-Object {
            Write-Host "   PID: $($_.Id) | Memory: $([math]::Round($_.WorkingSet64/1MB, 2))MB" -ForegroundColor Gray
        }
    }
    
    # Check connected devices
    Write-Host "📱 Connected Android Devices:" -ForegroundColor Blue
    try {
        $devices = adb devices 2>$null
        if ($devices) {
            Write-Host $devices -ForegroundColor Gray
        } else {
            Write-Host "   ⚠️  ADB not found or no devices connected" -ForegroundColor Yellow
        }
    } catch {
        Write-Host "   ⚠️  ADB not available (Android SDK not installed)" -ForegroundColor Yellow
    }
}

# Main execution
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