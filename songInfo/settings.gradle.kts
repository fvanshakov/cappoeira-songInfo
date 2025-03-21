plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "songInfo"
include("adminBoardClient")
include("app")
include("utils")
include("songInfoDB")
