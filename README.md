# Eng 1 - Team 8

## Setting Up VS-code and Java

-   Install **Visual Studio Code** (Download [here](https://code.visualstudio.com/Download))
-   Install **JavaJDK** (this will run Java program for you) [I would recommend [OpenJDK11 HotSpot](https://adoptopenjdk.net/)]
-   Add `JAVA_HOME` = `YourDrive:\Program Files\AdoptOpenJDK\jdk-xx.x.xx.x-hotspot` (this is relative path and it will depend upon your JavaJDK installation path.
-   Make sure you have got [Extension Pack for Java](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack) installed to make your life much easier.

Note: This will change and vary as we add more dependencies or libraries in our project.

## Setting Up Repo

-   `git clone https://github.com/harshonyou/eng1-team8.git`
-   Current directory structure looks like this. Where `deltaducks` have got Maven Java Project and `docs` have got static webpages. (It is subject to change)

```
├─── deltaducks
│   └── src
│       ├── main
│       └── test
└─── docs
    ├── scripts
    └── styles
```

-   Using VS-code you can compile and run the whole project using `Java Projects` tab on the bottom-left using `play` button over `deltaducks`.
    ![vscode-snipit](https://i.ibb.co/kq8qFfk/Screenshot-2021-11-18-190003.png)
-   It should succesfully compile and print `Hello World!`

### Contributors

-   Viktor Atta-Darkua
-   Danny Burrows
-   Zac Challis
-   Dandi Harmanto
-   Harsh Mohan
-   Yumis Zyutyu
