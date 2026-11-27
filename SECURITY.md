# Security Guidelines for IgniSign Android SDK

## Overview
This document outlines security best practices when integrating the IgniSign Android SDK into your application.

## Critical Security Requirements

### 1. Never Hardcode Credentials
- **NEVER** hardcode API keys, tokens, or secrets in your source code
- Always fetch sensitive credentials from your secure backend API
- Use environment variables or secure configuration for development

### 2. Secure Token Handling
- Store tokens securely using Android Keystore or EncryptedSharedPreferences
- Never log tokens, secrets, or other sensitive data
- Clear tokens from memory when no longer needed

### 3. Network Security
- The SDK only loads the signing session over HTTPS (`https://sign.ignisign.io`)
- Cleartext policy (`usesCleartextTraffic` / `networkSecurityConfig`) is owned by your app; the SDK manifest does not set it
- The example app is HTTPS-only in release builds; cleartext to localhost/emulator/ngrok development hosts is allowed only in its debug build (`ignisign-example/src/debug/res/xml/network_security_config.xml`)

### 4. WebView Security
- Top-level WebView navigation is restricted to HTTPS `ignisign.io` hosts; other http(s) links open in the external browser
- Bridge messages are only accepted from `https://*.ignisign.io` origins, and `OPEN_URL` requests are validated the same way
- Sensitive parameters are redacted from debug logs

### 5. Backup and Data Extraction
- The SDK does not set `android:allowBackup` (that would conflict with your manifest); configure backup rules in your app
- The example app disables backup and excludes shared preferences and databases

## Implementation Checklist

- [ ] Remove hardcoded credentials from example code
- [ ] Implement secure token storage (Android Keystore or EncryptedSharedPreferences)
- [ ] Fetch credentials from your secure backend API
- [ ] Remove development domain exceptions from `network_security_config.xml` for production
- [ ] Implement token refresh mechanism
- [ ] Add certificate pinning for critical API endpoints (recommended)
- [ ] Enable ProGuard/R8 obfuscation for release builds
- [ ] Test with Android Debug Bridge (ADB) disabled

## Reporting Security Issues

If you discover a security vulnerability, please email security@ignisign.io with:
- Description of the vulnerability
- Steps to reproduce
- Potential impact
- Suggested fix (if available)

Do not open public GitHub issues for security vulnerabilities.
