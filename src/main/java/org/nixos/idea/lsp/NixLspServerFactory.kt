package org.nixos.idea.lsp


import com.intellij.execution.ui.ConsoleViewContentType
import com.intellij.openapi.project.Project
import com.koxudaxi.ruff.NixLspLogging
import com.redhat.devtools.lsp4ij.LanguageServerFactory
import com.redhat.devtools.lsp4ij.LanguageServerEnablementSupport
import com.redhat.devtools.lsp4ij.client.LanguageClientImpl
import com.redhat.devtools.lsp4ij.server.OSProcessStreamConnectionProvider
import com.redhat.devtools.lsp4ij.server.StreamConnectionProvider

import org.jetbrains.annotations.NotNull


class NixLspServerFactory : LanguageServerFactory, LanguageServerEnablementSupport {
    private var enabled = true;


    @NotNull
    public fun createConnectionProvider(@NotNull project: Project?): OSProcessStreamConnectionProvider {
        return NixLspServer(project)
    }

    override fun createConnectionProvider(p0: Project): StreamConnectionProvider {
        TODO("Not yet implemented")
    }

    override fun isEnabled(project: Project): Boolean {
        try {
            if (!enabled) return false;
            val settings = NixLspSettings.getInstance();

            if (settings.command === "") {
                NixLspLogging.log(project, "A Command is not set for lsp, Please set a command for lsp for it to be enabled.",
                    ConsoleViewContentType.NORMAL_OUTPUT)
                return false
            }

            return true;
        } catch (e: Exception) {
            NixLspLogging.log(project, "Error with enabling LSP: ${e.message}", ConsoleViewContentType.ERROR_OUTPUT)
        }
    }

    override fun setEnabled(enable: Boolean, project: Project) {
        this.enabled = enable;
        if (!enable) {
            NixLspLogging.log(
                project,
                "Nix LSP4IJ setEnabled(false) -- Doing nothing here, letting LSP4IJ handle stop.",
                ConsoleViewContentType.NORMAL_OUTPUT
            )
        }
    }

//    @NotNull  // If you need to provide client specific features
//    public fun createLanguageClient(@NotNull project: Project?): LanguageClientImpl {
//        return MyLanguageClient(project)
//    }

//    @NotNull  // If you need to expose a custom server API
//    override fun getServerInterface(): Class<out LanguageServer?> {
//        return MyCustomServerAPI::class.java
//    }
}