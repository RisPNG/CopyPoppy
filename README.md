[![Ko-fi](https://img.shields.io/badge/Support%20me%20on-Ko--fi-FF5E5B.svg?logo=ko-fi)](https://ko-fi.com/RisPNG)
[![PayPal](https://img.shields.io/badge/Donate%20on-PayPal-00457C.svg?logo=paypal)](https://paypal.me/itsris)
[![Chat with Me!](https://img.shields.io/badge/Discord-chat-7289da.svg?&logo=discord)](https://discord.gg/xnwRcyPn)

      ██▌▄██▄
     ▄██████   ▄▀▀▀▀▀  ▄▀▀▀▀▀▄  █▀▀▀▀▄  █   █  █▀▀▀▀▄  ▄▀▀▀▀▀▄  █▀▀▀▀▄ █▀▀▀▀▄ █   █
    ▀████████  █      █       █ █    █   ▀▄▄█  █    █ █       █ █    █ █    █  ▀▄▄█
       ▐█▀█▄   █      █       █ █▀▀▀▀      █   █▀▀▀▀  █       █ █▀▀▀▀  █▀▀▀▀     █
            █  ▀▄▄▄▄▄  ▀▄▄▄▄▄▀  █         █    █       ▀▄▄▄▄▄▀  █      █        █                          asciiart.club

# CopyPoppy - A Source Code Plagiarism Detection Tool
<sub>Version 0.1b</sub>

https://ieeexplore.ieee.org/document/10001740

This tool detects plagiarism among file submissions. It is still in beta state as more features have yet to come. Some of it are:

1. Improve tokenization algorithm to separate string tokens after every `;` and `{`.
1. Set local database path.
1. Input code to be excluded.
1. File sort and rename.
1. Pleasing visual result report.
1. Input external tokenization algorithms.

### System Requirements
 |Minimum Requirements|Recommended
----|----|----
OS|Windows 8.1|Latest Windows 10 Build
CPU|32-bit Single Core|64-bit Dual Core or Higher
RAM (Memory)|40MB Free|100MB Free
Disk (Storage)|5MB Free|100MB Free

### Prerequisites

IDE: [IntelliJ IDEA 2021.3 or Higher](https://www.jetbrains.com/idea/download)

Java: [JDK 17.0.2 or Higher](https://corretto.aws/downloads/latest/amazon-corretto-17-x64-windows-jdk.msi)

External Libraries (JAR): [Commons IO 2.11.0](https://dlcdn.apache.org//commons/io/binaries/commons-io-2.11.0-bin.zip), [Commons Lang3 3.12.0](https://dlcdn.apache.org//commons/lang/binaries/commons-lang3-3.12.0-bin.zip)

External Library (Maven): com.github.javaparser:javaparser-core:3.24.2

## Compiling

1. Download code as ZIP and extract into `CopyPoppy` folder.
1. `Open Folder as IntelliJ IDEA Project`.
1. Import external libraries as project dependencies.
1. Run CopyPoppy.

## Contributing

No contributing rules of coding and pull requests are established yet.

## License

CopyPoppy is free/libre and open source software, it is using the GPLv3 license.

See [LICENSE](LICENSE) for the full license text.
