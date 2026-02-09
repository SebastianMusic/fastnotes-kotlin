package no.sebbe.fnkotlin

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform