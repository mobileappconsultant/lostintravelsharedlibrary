import java.io.File

class VersionManager(val file: File) {
    private var major: Int = 0
    private var minor: Int = 0
    private var code: Int = 0
    private var groupId: String = ""

    init {
        var versionString = file.readText()
        var stringArray = versionString.split("\n")
        stringArray.forEach { entry ->
            var splitEntry = entry.split("=")

            when (splitEntry.first()) {
                "major" -> {
                    major = splitEntry[1].toInt()
                }
                "minor" -> {
                    minor = splitEntry[1].toInt()
                }
                "code" -> {
                    code = splitEntry[1].toInt()
                }
                "group_id" -> {
                    groupId = splitEntry[1]
                }
            }
        }
    }

    fun bumpMajor() {
        major += 1
    }

    fun bumpMinor() {
        minor += 1
    }

    fun bumpCode() {
        code += 1
    }

    fun setGroupId(groupId: String) {
        this.groupId = groupId
    }

    fun versionToString(): String {
        return "${major}.${minor}.${code}-${groupId}"
    }

    fun saveVersionString() {
        val versionString = java.lang.StringBuilder().apply {
            append("major=${major}\n")
            append("minor=${minor}\n")
            append("code=${code}\n")
            append("group_id=${groupId}\n")
        }.toString()
        file.writeText(versionString)
    }
}