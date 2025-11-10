# Appium Mobile Automation

Simple mobile app testing framework using Appium + Java. Tests login functionality on Android devices with the Sauce Labs Demo App.

## What it does

- Launches mobile app on Android device/emulator
- Tests login with username and password
- Verifies successful login

## Requirements

- Java 11+
- Maven
- Node.js
- Android Studio (for emulator)

## Quick Start

1. Install dependencies:
   ```bash
   mvn clean install
   npm install -g appium
   ```

2. Start Android emulator or connect device

3. Run test:
   ```bash
   appium &
   mvn test
   ```

## Test Details

- **App**: Sauce Labs Demo App (downloads automatically)
- **Login**: username: `standard_user`, password: `secret_sauce`
- **Platform**: Android emulator or device

## Troubleshooting

- **No devices**: Check `adb devices` shows your emulator/device
- **Appium not found**: Make sure you ran `npm install -g appium`
- **Test fails**: Ensure emulator is fully loaded before running test

That's it! Simple mobile automation testing framework ready to use.