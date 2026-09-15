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
- The SDK now enforces HTTPS by default
- Cleartext traffic is only allowed for localhost/development domains
- For production builds, remove development domain exceptions from `network_security_config.xml`

### 4. WebView Security
- The SDK implements URL validation to prevent navigation to malicious sites
- JavaScript interfaces are scoped to the IgniSign domain only
- Sensitive parameters are redacted from debug logs

### 5. Backup and Data Extraction
- The SDK excludes sensitive data from Android backups by default
- If you need to include specific data in backups, explicitly configure it in `backup_rules.xml`

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
