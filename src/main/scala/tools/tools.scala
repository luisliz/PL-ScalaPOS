package tools

import sys.process._

class tools {
	def listen() {
		print("Listening:")
		val result = "python3 SpeechRecog.py" ! ProcessLogger(stdout append _, stderr append _)

		print(result)
		val add = "add"
		val remove = "remove"

	}
}
