package nightgames.areas;

import nightgames.actions.Movement;

import java.awt.*;
import java.util.HashMap;

/**
 * The default college map.
 */
public class MapSchool {
    public static HashMap<String, Area> buildMap() {
        Area quad = new Area("Quad",
                        "You are in the <b>Quad</b> that sits in the center of the Dorm, the Dining Hall, the Engineering Building, and the Liberal Arts Building. There's "
                                        + "no one around at this time of night, but the Quad is well-lit and has no real cover. You can probably be spotted from any of the surrounding buildings, it may "
                                        + "not be a good idea to hang out here for long.",
                        Movement.quad, new MapDrawHint(new Rectangle(10, 3, 7, 9), "Quad", false));
        Area dorm = new Area("Dorm",
                        "You are in the <b>Dorm</b>. Everything is quieter than it would be in any other dorm this time of night. You've been told the entire first floor "
                                        + "is empty during match hours, but you wouldn't be surprised if a few of the residents are hiding in their rooms, peeking at the fights. You've stashed some clothes "
                                        + "in one of the rooms you're sure is empty, which is common practice for most of the competitors.",
                        Movement.dorm, new MapDrawHint(new Rectangle(14, 12, 3, 5), "Dorm", false));
        Area shower = new Area("Showers",
                        "You are in the first floor <b>Showers</b>. There are a half-dozen stalls shared by the residents on this floor. They aren't very big, but there's "
                                        + "room to hide if need be. A hot shower would help you recover after a tough fight, but you'd be vulnerable if someone finds you.",
                        Movement.shower, new MapDrawHint(new Rectangle(13, 17, 4, 2), "Showers", false));
        Area laundry = new Area("Laundry Room",
                        "You are in the <b>Laundry Room</b> in the basement of the Dorm. Late night is prime laundry time in your dorm, but none of these machines "
                                        + "are running. You're a bit jealous when you notice that the machines here are free, while yours are coin-op. There's a tunnel here that connects to the basement of the "
                                        + "Dining Hall.",
                        Movement.laundry, new MapDrawHint(new Rectangle(17, 15, 8, 2), "Laundry", false));
        Area engineering = new Area("Engineering",
                        "You are in the Science and <b>Engineering Building</b>. Most of the lecture rooms are in other buildings; this one is mostly "
                                        + "for specialized rooms and labs. The first floor contains workshops mostly used by the Mechanical and Electrical Engineering classes. The second floor has "
                                        + "the Biology and Chemistry Labs. There's a third floor, but that's considered out of bounds.",
                        Movement.engineering, new MapDrawHint(new Rectangle(10, 0, 7, 3), "Eng", false));
        Area lab = new Area("Chemistry Lab",
                        "You are in the <b>Chemistry Lab</b>. The shelves and cabinets are full of all manner of dangerous and/or interesting chemicals. A clever enough "
                                        + "person could combine some of the safer ones into something useful. Just outside the lab is a bridge connecting to the library.",
                        Movement.lab, new MapDrawHint(new Rectangle(0, 0, 10, 3), "Lab", false));
        Area workshop = new Area("Workshop",
                        "You are in the Mechanical Engineering <b>Workshop</b>. There are shelves of various mechanical components and the back table is covered "
                                        + "with half-finished projects. A few dozen Mechanical Engineering students use this workshop each week, but it's well stocked enough that no one would miss "
                                        + "some materials that might be of use to you.",
                        Movement.workshop, new MapDrawHint(new Rectangle(17, 0, 8, 3), "Workshop", false));
        Area libarts = new Area("Liberal Arts",
                        "You are in the <b>Liberal Arts Building</b>. There are three floors of lecture halls and traditional classrooms, but only "
                                        + "the first floor is in bounds. The Library is located directly out back, and the side door is just a short walk from the pool.",
                        Movement.la, new MapDrawHint(new Rectangle(5, 5, 5, 7), "L&A", false));
        Area pool = new Area("Pool",
                        "You are by the indoor <b>Pool</b>, which is connected to the Student Union for reasons that no one has ever really explained. The pool here is quite "
                                        + "large and there is even a jacuzzi. A quick soak would feel good, but the lack of privacy is a concern. The side doors are locked at this time of night, but the "
                                        + "door to the Student Union is open and there's a back door that exits near the Liberal Arts building. Across the water in the other direction is the Courtyard.",
                        Movement.pool, new MapDrawHint(new Rectangle(6, 12, 4, 2), "Pool", false));
        Area library = new Area("Library",
                        "You are in the <b>Library</b>. It's a two floor building with an open staircase connecting the first and second floors. The front entrance leads to "
                                        + "the Liberal Arts building. The second floor has a Bridge connecting to the Chemistry Lab in the Science and Engineering building.",
                        Movement.library, new MapDrawHint(new Rectangle(0, 8, 5, 12), "Library", false));
        Area dining = new Area("Dining Hall",
                        "You are in the <b>Dining Hall</b>. Most students get their meals here, though some feel it's worth the extra money to eat out. The "
                                        + "dining hall is quite large and your steps echo on the linoleum, but you could probably find someplace to hide if you need to.",
                        Movement.dining, new MapDrawHint(new Rectangle(17, 6, 4, 6), "Dining", false));
        Area kitchen = new Area("Kitchen",
                        "You are in the <b>Kitchen</b> where student meals are prepared each day. The industrial fridge and surrounding cabinets are full of the "
                                        + "ingredients for any sort of bland cafeteria food you can imagine. Fortunately, you aren't very hungry. There's a chance you might be able to cook up some "
                                        + "of the more obscure items into something useful.",
                        Movement.kitchen, new MapDrawHint(new Rectangle(18, 12, 4, 2), "Kitchen", false));
        Area storage = new Area("Storage Room",
                        "You are in a <b>Storage Room</b> under the Dining Hall. It's always unlocked and receives a fair bit of foot traffic from students "
                                        + "using the tunnel to and from the Dorm, so no one keeps anything important in here. There's enough junk down here to provide some hiding places and there's a chance "
                                        + "you could find something useable in one of these boxes.",
                        Movement.storage, new MapDrawHint(new Rectangle(21, 6, 4, 5), "Storage", false));
        Area tunnel = new Area("Tunnel",
                        "You are in the <b>Tunnel</b> connecting the dorm to the dining hall. It doesn't get a lot of use during the day and most of the freshmen "
                                        + "aren't even aware of its existence, but many upperclassmen have been thankful for it on cold winter days and it's proven to be a major tactical asset. The "
                                        + "tunnel is well-lit and doesn't offer any hiding places.",
                        Movement.tunnel, new MapDrawHint(new Rectangle(23, 11, 2, 4), "Tunnel", true));
        Area bridge = new Area("Bridge",
                        "You are on the <b>Bridge</b> connecting the second floors of the Science and Engineering Building and the Library. It's essentially just a "
                                        + "corridor, so there's no place for anyone to hide.",
                        Movement.bridge, new MapDrawHint(new Rectangle(0, 3, 2, 5), "Bridge", true));
        Area sau = new Area("Student Union",
                        "You are in the <b>Student Union</b>, which doubles as base of operations during match hours. You and the other competitors can pick up "
                                        + "a change of clothing here.",
                        Movement.union, new MapDrawHint(new Rectangle(10, 12, 3, 5), "S.Union", true));
        Area courtyard = new Area("Courtyard",
                        "You are in the <b>Courtyard</b>. "
                                        + "It's a small clearing behind the school pool. There's not much to see here except a tidy garden maintained by the botany department.",
                        Movement.courtyard, new MapDrawHint(new Rectangle(6, 14, 3, 6), "Courtyard", true));
        quad.link(dorm);
        quad.link(engineering);
        quad.link(libarts);
        quad.link(dining);
        quad.link(sau);
        dorm.link(shower);
        dorm.link(laundry);
        dorm.link(quad);
        shower.link(dorm);
        laundry.link(dorm);
        laundry.link(tunnel);
        engineering.link(quad);
        engineering.link(lab);
        engineering.link(workshop);
        workshop.link(engineering);
        lab.link(engineering);
        lab.link(bridge);
        lab.jump(dining);
        libarts.link(quad);
        libarts.link(library);
        libarts.link(pool);
        pool.link(libarts);
        pool.link(sau);
        pool.link(courtyard);
        courtyard.link(pool);
        library.link(libarts);
        library.link(bridge);
        dining.link(quad);
        dining.link(storage);
        dining.link(kitchen);
        kitchen.link(dining);
        storage.link(dining);
        storage.link(tunnel);
        tunnel.link(storage);
        tunnel.link(laundry);
        bridge.link(lab);
        bridge.link(library);
        bridge.jump(quad);
        sau.link(pool);
        sau.link(quad);
        workshop.shortcut(pool);
        pool.shortcut(workshop);
        library.shortcut(tunnel);
        tunnel.shortcut(library);
        HashMap<String, Area> map = new HashMap<>();
        map.put("Quad", quad);
        map.put("Dorm", dorm);
        map.put("Shower", shower);
        map.put("Laundry", laundry);
        map.put("Engineering", engineering);
        map.put("Workshop", workshop);
        map.put("Lab", lab);
        map.put("Liberal Arts", libarts);
        map.put("Pool", pool);
        map.put("Library", library);
        map.put("Dining", dining);
        map.put("Kitchen", kitchen);
        map.put("Storage", storage);
        map.put("Tunnel", tunnel);
        map.put("Bridge", bridge);
        map.put("Union", sau);
        map.put("Courtyard", courtyard);
        return map;
    }
}
