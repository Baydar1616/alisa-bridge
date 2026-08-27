# Porcupine model placement

This repository does not include Porcupine (.ppn) keyword model files due to licensing. To enable Porcupine detection:

1. Obtain or generate keyword models from the Picovoice Console or SDK as permitted by their license.
2. Place .ppn model files under app/src/main/assets/porcupine/
   - e.g. app/src/main/assets/porcupine/alisa.ppn
3. Implement wiring in PorcupineEngine to load the specific model filenames and start the native engine. The current PorcupineEngine.kt is a stub with initialization comments.

Fallback: If you prefer open-source on-device ASR (e.g., Vosk) for arbitrary Russian phrases, add model files and enable the VoskEngine implementation (not included by default due to large model sizes).
