package modules

import java.lang.Iterable
import java.nio.file.{Path, Paths}
import java.util.Collections
import java.util.concurrent.TimeUnit

import com.google.inject.{Provides, Singleton}
import com.twitter.inject.TwitterModule
import com.twitter.inject.annotations.Flag
import org.cfg4j.provider.{ConfigurationProvider, ConfigurationProviderBuilder}
import org.cfg4j.source.context.environment.ImmutableEnvironment
import org.cfg4j.source.context.filesprovider.ConfigFilesProvider
import org.cfg4j.source.git.GitConfigurationSourceBuilder
import org.cfg4j.source.reload.strategy.PeriodicalReloadStrategy

object ConfigurationModule extends TwitterModule {

  flag("repo.path", "https://github.com/mskimm/cfg4j-sample-config.git", "configRepoPath")
  flag("branch", "master", "branch")

  @Singleton
  @Provides
  def configurationProvider(
    @Flag("repo.path") configRepoPath: String,
    @Flag("branch") branch: String): ConfigurationProvider = {

    // for private repo.
    // import org.eclipse.jgit.transport.{UsernamePasswordCredentialsProvider, CredentialsProvider}
    // CredentialsProvider.setDefault(new UsernamePasswordCredentialsProvider(
    //   "your github account",
    //   "your github token"))

    val source = new GitConfigurationSourceBuilder()
      .withRepositoryURI(configRepoPath)
      .withConfigFilesProvider(new ConfigFilesProvider {
        override def getConfigFiles: Iterable[Path] =
          Collections.singletonList(Paths.get("application.yaml"))
      })
      .build()

    // Select branch to use (use new DefaultEnvironment()) for master
    val environment = new ImmutableEnvironment(branch)

    // Reload configuration every 1 minute
    val reloadStrategy = new PeriodicalReloadStrategy(1, TimeUnit.MINUTES)

    // Create provider
    new ConfigurationProviderBuilder()
      .withConfigurationSource(source)
      .withEnvironment(environment)
      .withReloadStrategy(reloadStrategy)
      .build()
  }

}
