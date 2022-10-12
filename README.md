# Entranced

An enchantment mod

## How to export as a JAR file
1. Run in a terminal from the same directory as the project directory `./gradlew build` on GNU/Linux and Mac, or `gradlew build` on Windows.
2. Alternatively, in IntelliJ IDEA, open the Gradle tab on the right and execute `build` under `Tasks` → `build`. After this is done once, the `build` task should appear in the run configurations.
3. The JAR should appear in `${projectDir}/forge/build/libs` for Forge, and `${projectDir}/fabric/build/libs` for Fabric. Use the JAR with the longest filename.

# Installing
1. Install the Forge or Fabric modloader.
2. Place the JAR file in the mods folder along with its dependencies.
