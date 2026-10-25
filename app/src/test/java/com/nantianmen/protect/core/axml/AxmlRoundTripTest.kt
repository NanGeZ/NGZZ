package com.nantianmen.protect.core.axml

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class AxmlRoundTripTest {

    @Test
    fun buildAndParseRoundTrip() {
        val root = XmlElement("manifest")
        root.attr("package", "com.example.demo")
        root.attr("android:versionCode", 3)
        val app = root.child("application") {
            attr("android:name", "com.example.demo.App")
            attr("android:allowBackup", true)
            child("activity") {
                attr("android:name", ".MainActivity")
            }
        }
        val bytes = AxmlBuilder.build(root)

        val parsed = AxmlParser.parse(bytes)
        assertEquals("manifest", parsed.name)
        assertEquals("com.example.demo", parsed.attr("package"))
        assertEquals("3", parsed.attr("android:versionCode"))

        val parsedApp = parsed.find("application")
        assertNotNull(parsedApp)
        assertEquals("com.example.demo.App", parsedApp!!.attr("android:name"))
        assertEquals("true", parsedApp.attr("android:allowBackup"))

        val activity = parsedApp.find("activity")
        assertNotNull(activity)
        assertEquals(".MainActivity", activity!!.attr("android:name"))

        assertEquals(1, app.children.size)
    }
}
