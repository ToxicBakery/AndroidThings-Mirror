package com.toxicbakery.library.ical

import org.junit.Assert.assertEquals
import org.junit.Test

class IcalReaderTest {

    val icalReader = IcalReader()
    val input = """BEGIN:VCALENDAR
PRODID:-//Google Inc//Google Calendar 70.9054//EN
VERSION:2.0
CALSCALE:GREGORIAN
METHOD:PUBLISH
X-WR-CALNAME:Joint Calendar
X-WR-TIMEZONE:America/New_York
X-WR-CALDESC:
BEGIN:VEVENT
DTSTART:20170823T223000Z
DTEND:20170824T013000Z
DTSTAMP:20171118T224627Z
UID:1mqfi8r283u9b46vii4dartaqp@google.com
CREATED:20170822T180809Z
DESCRIPTION:For full details\, including the address\, and to RSVP see:\nht
 tps://www.meetup.com/Central-Florida-Android-Developers-Group/events/242591
 520/\nCentral Florida Android Developers Group\nThey say August is the Sund
 ay of summer so come chill out Wednesday\, August 23rd\, for another instal
 lment of Android talks.\nFlux the World - Re...
LAST-MODIFIED:20170822T180809Z
LOCATION:Canvs - 101 S Garland Ave\, Suite 108 - Orlando\, FL
SEQUENCE:0
STATUS:CONFIRMED
SUMMARY:Android August Meetup
TRANSP:OPAQUE
END:VEVENT
BEGIN:VEVENT
DTSTART:20170823T223000Z
DTEND:20170824T013000Z
DTSTAMP:20171118T224627Z
UID:1mqfi8r283u9b46vii4dartaqp@google.com
CREATED:20170822T180809Z
DESCRIPTION:For full details\, including the address\, and to RSVP see:\nht
 tps://www.meetup.com/Central-Florida-Android-Developers-Group/events/242591
 520/\nCentral Florida Android Developers Group\nThey say August is the Sund
 ay of summer so come chill out Wednesday\, August 23rd\, for another instal
 lment of Android talks.\nFlux the World - Re...
LAST-MODIFIED:20170822T180809Z
LOCATION:Canvs - 101 S Garland Ave\, Suite 108 - Orlando\, FL
SEQUENCE:0
STATUS:CONFIRMED
SUMMARY:Android August Meetup
TRANSP:OPAQUE
END:VEVENT
END:VCALENDAR"""

    @Test
    fun readIcal() {
        icalReader.readIcal(input.byteInputStream()
                .bufferedReader())
                .apply {
                    assertEquals("2.0", values["VERSION"])
                    assertEquals(2, blocks.size)
                }
    }

}