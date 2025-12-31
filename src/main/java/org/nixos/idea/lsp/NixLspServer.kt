package org.nixos.idea.lsp


import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.util.execution.ParametersListUtil
import com.redhat.devtools.lsp4ij.server.ProcessStreamConnectionProvider
import org.apache.tools.ant.Project

class NixLspServer(project: Project) : ProcessStreamConnectionProvider() {

    init {
        val settings = NixLspSettings.getInstance();
        val commandLine = GeneralCommandLine(settings.command)
        setCommands(commandLine)
    }
}