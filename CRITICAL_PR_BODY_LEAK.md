# 🚨 CRITICAL: PR #1 DESCRIPTION CONTAINS LEAKED SECRETS

**Date:** 2026-10-12  
**Severity:** CRITICAL  
**Status:** REQUIRES IMMEDIATE MANUAL ACTION

## Problem

Pull request #1 (https://github.com/ignisign/ignisign-android/pull/1) contains **exposed credential values** directly in the PR description body.

The PR body currently displays:
- Full session token value (64 characters)
- Full auth secret UUID
- Signature request ID
- Signer ID

This is a **secondary leak** - the credentials exist in git history (commit `066e793`), but pasting them into the PR description exposes them again in a highly visible location.

## Why This Is Critical

1. **GitHub PR descriptions are indexed and cached** - even after editing, old versions may remain in caches
2. **API access** - PR descriptions are accessible via GitHub API to anyone with read access
3. **Notifications** - Email/Slack notifications may have already sent the full text with secrets
4. **Search engines** - Public repos: PR text may be indexed by search engines

## Required Action

**MANUAL EDIT REQUIRED** - The cloud agent lacks permissions to edit PR descriptions via the GitHub API.

A repository owner/admin with edit permissions must:

1. Navigate to: https://github.com/ignisign/ignisign-android/pull/1
2. Click "Edit" on the PR description
3. Replace all credential VALUES with generic references:
   - Replace session token string with: `[REDACTED - see commit 066e793]`
   - Replace auth secret UUID with: `[REDACTED - see commit 066e793]`
   - Replace signature request ID with: `[REDACTED - see commit 066e793]`
   - Replace signer ID with: `[REDACTED - see commit 066e793]`
4. Keep file paths and commit SHAs (those are metadata, not secrets)
5. Save the edited description

## Reference

The corrected PR body has been prepared in:
- `/tmp/pr_body_scrubbed.md` (in this agent session)
- The scrubbed version removes all secret values while preserving context

## Follow-up

Once the PR description is manually scrubbed:
1. Delete this file (`CRITICAL_PR_BODY_LEAK.md`)
2. The underlying credentials in commit `066e793` still require rotation per the PR checklist
3. Consider GitHub's Security Advisory feature for broader credential revocation notification

---

**This file will be committed to the PR branch to ensure visibility of this critical issue.**
