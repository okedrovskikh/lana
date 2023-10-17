package lana.deploy

@JvmInline
value class TerminalCommand(private val command: String) {

    fun runCommand(): Process = ProcessBuilder(*command.split(" ").toTypedArray())
        .redirectInput(ProcessBuilder.Redirect.PIPE)
        .redirectOutput(ProcessBuilder.Redirect.PIPE)
        .redirectErrorStream(true)
        .start()
}
