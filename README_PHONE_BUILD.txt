MARCO CLIENT — PHONE-ONLY BUILD

1. Create a GitHub account or sign in using your phone browser.
2. Create a NEW public or private repository named MarcoClient.
3. Upload the CONTENTS of this folder (including the hidden .github folder).
4. Open the repository's Actions tab and select "Build Marco Client APK".
5. Tap "Run workflow" if it has not started automatically.
6. Wait for the green successful run. Open the run and download the artifact named MarcoClient-debug-apk.
7. Extract the downloaded ZIP, tap app-debug.apk, and follow Android's install prompt.

This is a debug/test APK, not a Play Store release. Keep the repository private if you prefer not to publish the source.

IMPORTANT FEATURE LIMITATIONS:
- The app stores local preferences and opens the separately installed Minecraft app.
- Toggles in this starter build are preference switches; they do NOT modify Minecraft by themselves.
- In-game HUD/visual effects need separate compatible Bedrock resource-pack/add-on integration.
- X-ray and automated combat features are intentionally not included.
- The account page uses a local display name only; never enter your Microsoft password into a third-party client.
