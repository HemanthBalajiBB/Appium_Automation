#!/usr/bin/env pwsh
# Test Runner Script for Appium Automation Framework

param(
    [string]$TestClass = "",
    [string]$Platform = "Android",
    [switch]$StartAppium,
    [switch]$CheckSetup
)

function Write-Banner {
    param([string]$Text, [string]$Color = "Cyan")
    Write-Host ""
    Write-Host ("=" * 60) -ForegroundColor $Color
    Write-Host " $Text" -ForegroundColor $Color
    Write-Host ("=" * 60) -ForegroundColor $Color
}

function Test-Prerequisites {
    Write-Banner "🔍 Checking Prerequisites" "Blue"
    
    $issues = @()
    
    # Check Java
    try {
        $javaVersion = java -version 2>&1 | Select-String "version"
        Write-Host "✅ Java: $($javaVersion.Line.Split('"')[1])" -ForegroundColor Green
    } catch {
        $issues += "❌ Java not found"
    }
    
    # Check Node.js
    try {
        $nodeVersion = node --version
        Write-Host "✅ Node.js: $nodeVersion" -ForegroundColor Green
    } catch {
        $issues += "❌ Node.js not found"
    }
    
    # Check Appium
    try {
        $appiumVersion = appium --version
        Write-Host "✅ Appium: $appiumVersion" -ForegroundColor Green
    } catch {
        $issues += "❌ Appium not found"
    }
    
    # Check Maven
    try {
        $mvnVersion = mvn --version | Select-String "Apache Maven"
        Write-Host "✅ Maven: $($mvnVersion.Line)" -ForegroundColor Green
    } catch {
        $issues += "❌ Maven not found"
    }
    
    # Check Appium Server
    $serverRunning = netstat -an | findstr "4723"
    if ($serverRunning) {
        Write-Host "✅ Appium Server: Running on port 4723" -ForegroundColor Green
    } else {
        Write-Host "⚠️  Appium Server: Not running" -ForegroundColor Yellow
        if ($StartAppium) {
            Write-Host "🚀 Starting Appium Server..." -ForegroundColor Blue
            Start-Process -WindowStyle Minimized -FilePath "appium" -ArgumentList "--address", "127.0.0.1", "--port", "4723"
            Start-Sleep -Seconds 5
            
            $serverCheck = netstat -an | findstr "4723"
            if ($serverCheck) {
                Write-Host "✅ Appium Server: Started successfully" -ForegroundColor Green
            } else {
                $issues += "❌ Failed to start Appium Server"
            }
        } else {
            $issues += "⚠️  Appium Server not running (use -StartAppium to auto-start)"
        }
    }
    
    # Check for devices (if ADB available)
    try {
        $devices = adb devices 2>$null | Select-String "device$"
        if ($devices) {
            Write-Host "✅ Android Devices: $($devices.Count) connected" -ForegroundColor Green
            foreach ($device in $devices) {
                Write-Host "   📱 $($device.Line)" -ForegroundColor Gray
            }
        } else {
            Write-Host "⚠️  Android Devices: None connected" -ForegroundColor Yellow
            $issues += "⚠️  No Android devices/emulators connected"
        }
    } catch {
        Write-Host "⚠️  ADB: Not available (Android SDK not installed)" -ForegroundColor Yellow
        $issues += "⚠️  Android SDK not installed"
    }
    
    if ($issues.Count -gt 0) {
        Write-Host ""
        Write-Host "🚨 Issues Found:" -ForegroundColor Red
        foreach ($issue in $issues) {
            Write-Host "   $issue" -ForegroundColor Red
        }
        return $false
    }
    
    Write-Host ""
    Write-Host "🎉 All prerequisites satisfied!" -ForegroundColor Green
    return $true
}

function Start-Tests {
    param([string]$Class, [string]$Platform)
    
    Write-Banner "🧪 Running Tests" "Green"
    
    # Set platform if different from default
    if ($Platform -ne "Android") {
        Write-Host "🔄 Setting platform to: $Platform" -ForegroundColor Blue
        # You can add platform-specific logic here
    }
    
    # Build Maven command
    $mvnCommand = "mvn clean test"
    
    if ($Class) {
        $mvnCommand += " -Dtest=$Class"
        Write-Host "🎯 Running specific test class: $Class" -ForegroundColor Blue
    } else {
        Write-Host "🎯 Running all tests" -ForegroundColor Blue
    }
    
    Write-Host "⚡ Executing: $mvnCommand" -ForegroundColor Yellow
    Write-Host ""
    
    # Execute Maven tests
    Invoke-Expression $mvnCommand
}

# Main execution
Write-Banner "🚀 Appium Test Runner" "Cyan"

if ($CheckSetup) {
    $setupOk = Test-Prerequisites
    if (-not $setupOk) {
        Write-Host ""
        Write-Host "💡 Setup Tips:" -ForegroundColor Yellow
        Write-Host "   • Install Android Studio for Android SDK and emulator" -ForegroundColor Gray
        Write-Host "   • Create and start an Android emulator, or connect a physical device" -ForegroundColor Gray
        Write-Host "   • Use -StartAppium to automatically start Appium server" -ForegroundColor Gray
        exit 1
    }
} else {
    Write-Host "ℹ️  Use -CheckSetup to verify prerequisites" -ForegroundColor Blue
}

if ($StartAppium) {
    # Prerequisites check will handle Appium server startup
    Test-Prerequisites | Out-Null
}

# Run tests
Start-Tests -Class $TestClass -Platform $Platform

Write-Banner "Test Execution Complete" "Green"