# ![Animotion](assets/banner.png)

A Spigot library and CLI tool for animating BlockBench models in Minecraft Java Edition.

## Features

- Display BlockBench models and animations directly in Minecraft.
- Automate the conversion process using the CLI tool:
    - Convert BlockBench models into Minecraft resource packs.
    - Generate Kotlin code to control models in a Spigot plugin.
- Uses packet-based rendering to minimize server load.

## Usage

```shell
$ java -jar animotion-converter-1.0.0-SNAPSHOT.jar
Usage: animotion-converter [<options>]

Options:
  -d, --directory=<path>  Resource pack path
  -o, --output=<path>     Output destination path
  -c, --code=<path>       Code destination path
  -f, --force             Answer yes to all confirmations
  --ignore-pack-format    Ignore unsupported pack_format error
  -v, --version           Show the version and exit
  -h, --help              Show this message and exit
```

Animotion converts BlockBench models into animated entities for Minecraft Java Edition. It provides both a Spigot library and a CLI tool to streamline the process.

### Resource Pack Generation (`-o` option)

- Converts BlockBench models into a Minecraft-compatible resource pack.
- Outputs textures, model JSON files, and other necessary resources.

**Example:**

```shell
$ java -jar animotion-converter-1.0.0-SNAPSHOT.jar -d path/to/resourcepack -o path/to/output
```

This command processes the specified resource pack and outputs the transformed assets to `path/to/output`.

### Kotlin Code Generation (`-c` option)

- Generates Kotlin source code to control animations in a Spigot plugin.
- Outputs classes and methods required for animation management.

**Example:**

```shell
$ java -jar animotion-converter-1.0.0-SNAPSHOT.jar -d path/to/resourcepack -c path/to/code
```

This command generates Kotlin code based on the specified resource pack and outputs it to `path/to/code`.

## Example (robit)

![](assets/example.png)

### Input Files

The input resource pack should be structured as follows:

```
C:\HOME\PROJECTS\ANIMOTION\CONVERTER\SRC\TEST\RESOURCES\PACKS\ROBIT
│   pack.mcmeta
│   
└───animotion
        robit.bbmodel
        settings.json
```

### Output Files

#### Resource Pack (`-o` option)

The generated resource pack will have the following structure:

```
C:\HOME\PROJECTS\ANIMOTION\ROBIT
│   pack.mcmeta
│   
├───animotion
│       robit.bbmodel
│       settings.json
│       
└───assets
    ├───animotion
    │   ├───models
    │   │   └───robit
    │   │           0.json
    │   │           1.json
    │   │           2.json
    │   │           3.json
    │   │           4.json
    │   │           5.json
    │   │           6.json
    │   │           7.json
    │   │
    │   └───textures
    │       └───item
    │           └───robit
    │                   0.png
    │
    └───minecraft
        └───models
            └───item
                    stick.json
```

##### Generated file:

###### assets/animotion/models/robit/0.json

```json
{"texture_size":[64,64],"textures":{"0":"animotion:item/robit/0"},"elements":[{"from":[5.5,8.000000000000007,8.0],"to":[10.5,11.000000000000007,8.0],"rotation":{"angle":0.0,"axis":"y","origin":[7.5,-14.249999999999993,7.5]},"faces":{"north":{"uv":[6.25,0.0,7.5,0.75],"texture":"#0"},"east":{"uv":[0.0,0.0,0.0,0.75],"texture":"#0"},"south":{"uv":[7.5,0.0,6.25,0.75],"texture":"#0"},"west":{"uv":[0.0,0.0,0.0,0.75],"texture":"#0"},"up":{"uv":[1.25,0.0,0.0,0.0],"texture":"#0"},"down":{"uv":[1.25,0.0,0.0,0.0],"texture":"#0"}}}]}
```

#### Kotlin Code (`-c` option)

The generated Kotlin code will have the following structure:

```
C:\HOME\PROJECTS\ANIMOTION\CODE
└───dev
    └───s7a
        └───animotion
            └───generated
                    Robit.kt
```

##### Generated code:

###### Robit.kt

```kotlin
@file:Suppress("ktlint")

package dev.s7a.animotion.generated

import dev.s7a.animotion.Animotion
import dev.s7a.animotion.AnimotionModel
import dev.s7a.animotion.`data`.Animation
import dev.s7a.animotion.`data`.Part
import kotlin.Suppress
import org.bukkit.Material
import org.bukkit.util.Vector

public class Robit(
  animotion: Animotion,
) : AnimotionModel(animotion) {
  private val gear: Part =
      part("animotion:robit_0", Material.STICK, 1, Vector(0.0, 23.749999999999993, 0.0), Vector(0.0, -22.5, 0.0))

  private val body: Part = part("animotion:robit_1", Material.STICK, 2, Vector(0.0, 0.0, 0.0))

  private val leftShoulder: Part =
      part("animotion:robit_2", Material.STICK, 3, Vector(-6.0, 14.5, 0.0))

  private val leftArm: Part = part("animotion:robit_3", Material.STICK, 4, Vector(-6.0, 14.5, 0.0))

  private val rightShoulder: Part =
      part("animotion:robit_4", Material.STICK, 5, Vector(6.0, 14.5, 0.0))

  private val rightArm: Part = part("animotion:robit_5", Material.STICK, 6, Vector(6.0, 14.5, 0.0))

  private val leftLeg: Part =
      part("animotion:robit_6", Material.STICK, 7, Vector(-2.0, 6.333333333333333, 0.0))

  private val rightLeg: Part =
      part("animotion:robit_7", Material.STICK, 8, Vector(2.0, 6.333333333333333, 0.0))

  public val standing: Animation =
      loopAnimation(1.0, gear to listOf(0.0 to rotation(0.0, 0.0, 0.0)))

  public val walking: Animation =
      loopAnimation(1.0, gear to listOf(0.0 to rotation(0.0, 0.0, 0.0), 1.0 to rotation(0.0, -180.0, 0.0)), leftShoulder to listOf(0.0 to rotation(-45.0, 0.0, 0.0), 0.5 to rotation(45.0, 0.0, 0.0), 1.0 to rotation(-45.0, 0.0, 0.0)), leftArm to listOf(0.0 to rotation(-45.0, 0.0, 0.0), 0.5 to rotation(45.0, 0.0, 0.0), 1.0 to rotation(-45.0, 0.0, 0.0)), rightShoulder to listOf(0.0 to rotation(45.0, 0.0, 0.0), 0.5 to rotation(-45.0, 0.0, 0.0), 1.0 to rotation(45.0, 0.0, 0.0)), rightArm to listOf(0.0 to rotation(45.0, 0.0, 0.0), 0.5 to rotation(-45.0, 0.0, 0.0), 1.0 to rotation(45.0, 0.0, 0.0)), leftLeg to listOf(0.0 to rotation(45.0, 0.0, 0.0), 0.5 to rotation(-45.0, 0.0, 0.0), 1.0 to rotation(45.0, 0.0, 0.0)), rightLeg to listOf(0.0 to rotation(-45.0, 0.0, 0.0), 0.5 to rotation(45.0, 0.0, 0.0), 1.0 to rotation(-45.0, 0.0, 0.0)))

  public val question: Animation =
      onceAnimation(2.125, gear to listOf(0.0 to rotation(0.0, 0.0, 0.0), 1.0 to rotation(0.0, -180.0, 0.0), 2.0 to rotation(0.0, -360.0, 0.0)), leftShoulder to listOf(0.25 to rotation(0.0, 0.0, 0.0), 0.7083333333333334 to rotation(-180.0, 0.0, 0.0), 1.4166666666666667 to rotation(-180.0, 0.0, 0.0), 1.875 to rotation(0.0, 0.0, 0.0)), leftArm to listOf(0.0 to rotation(0.0, 0.0, 0.0), 0.25 to rotation(0.0, 0.0, -25.0), 0.7083333333333334 to rotation(-180.0, 0.0, 50.0), 0.875 to rotation(-180.0, 0.0, 25.0), 1.0 to rotation(-180.0, 0.0, 35.0), 1.125 to rotation(-180.0, 0.0, 25.0), 1.25 to rotation(-180.0, 0.0, 35.0), 1.4166666666666667 to rotation(-180.0, 0.0, 50.0), 1.875 to rotation(0.0, 0.0, 0.0), 1.9583333333333333 to rotation(0.0, 0.0, -5.0), 2.0416666666666665 to rotation(0.0, 0.0, 2.5), 2.125 to rotation(0.0, 0.0, 0.0)))

  public val freeze: Animation =
      holdAnimation(2.5, leftArm to listOf(0.0 to rotation(0.0, 0.0, 0.0), 0.48333333333333334 to rotation(-90.0, 0.0, 0.0), 0.48333333333333334 to scale(1.0, 1.0, 1.0), 1.0 to scale(1.0, 0.7, 1.0), 1.1333333333333333 to scale(1.0, 0.7, 1.0), 1.2 to position(0.0, 0.0, 0.0), 1.2 to scale(1.0, 1.0, 1.0), 1.2166666666666666 to rotation(-90.0, 0.0, 0.0), 1.2833333333333334 to position(0.0, 0.0, -12.0), 1.3333333333333333 to rotation(-17.5, 0.0, 0.0), 1.3666666666666667 to position(0.0, -5.5, -18.0), 1.45 to rotation(0.0, 0.0, 0.0), 1.45 to position(0.0, -15.0, -20.0)))
}
```

##### Example:

###### Spawn

```kotlin
// animotion: Animotion
val robit = Robit(animotion)

// player: Player
// location: Location
robit.spawn(player, location)
```

###### Play animation

```kotlin
// robit: Robit
// player: Player
robit.standing.play(player)
robit.walking.play(player)
robit.question.play(player)
robit.freeze.play(player)
```

## Installation

To use the generated code, add the following dependency to your project:

### build.gradle.kts

```kotlin
repositories {
    maven(url = "https://s01.oss.sonatype.org/content/repositories/snapshots/")
}

dependencies {
    implementation("dev.s7a:Animotion:1.0.0-SNAPSHOT")
}
```

### Main class

```kotlin
class ExamplePlugin : JavaPlugin() {
    private val animotion = Animotion(this)

    override fun onLoad() {
        animotion.onLoad()
    }

    override fun onEnable() {
        animotion.onEnable()
    }

    override fun onDisable() {
        animotion.onDisable()
    }
}
```

## License

```
Animotion Custom License

Copyright (C) 2023 by wanko-zushi

This tool is protected by copyright law. Unauthorized modification or redistribution of the code is prohibited.

If you wish to use this tool, please comply with the following conditions:

1. Maintain the original copyright notice of this tool.
2. Modifying or redistributing this tool is not permitted.
3. In the event of a takedown request for the work by wanko-zushi, for any reason whatsoever, you must promptly remove the work.

This tool is provided without warranty. The author or copyright holder shall not be liable for any direct or indirect damages arising from the use of this tool.
```
