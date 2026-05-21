package noescape;

/**
 * ROOM FACTORY: Creates all 4 rooms from .env data based on the player's course.
 * Returns RoomBehavior[].
 * Each room implements RoomBehavior directly.
 *
 * OOP: Encapsulation - room creation logic hidden inside this class.
 *      Polymorphism  - all rooms stored as RoomBehavior type.
 */
class RoomFactory {

    public static RoomBehavior[] createRooms(EnvLoader env, String course) {

        // Pick prefix based on course
        String p = course.contains("Nursing") ? "NR_" : "CS_";

        RoomBehavior[] rooms = new RoomBehavior[4];

        rooms[0] = new Classroom(
            env.get(p + "ROOM1_NAME",   "Classroom 101"),
            false,
            env.get(p + "ROOM1_PUZZLE", "What is your field of study?"),
            env.get(p + "ROOM1_ANSWER", "unknown"),
            env.get(p + "ROOM1_CLUE",   "Think about your course."),
            env.get(p + "ROOM1_HINT",   "Check your ID card.")
        );

        rooms[1] = new LibraryRoom(
            env.get(p + "ROOM2_NAME",   "Library"),
            true,
            env.get(p + "ROOM2_PUZZLE", "What do you find on every shelf here?"),
            env.get(p + "ROOM2_ANSWER", "book"),
            env.get(p + "ROOM2_CLUE",   "Look around you."),
            env.get(p + "ROOM2_HINT",   "Starts with B.")
        );

        rooms[2] = new TsgRoom(
            env.get(p + "ROOM3_NAME",   "TSG"),
            true,
            env.get(p + "ROOM3_PUZZLE", "What does TSG help students with?"),
            env.get(p + "ROOM3_ANSWER", "tech support"),
            env.get(p + "ROOM3_CLUE",   "TSG = Technology Support Group."),
            env.get(p + "ROOM3_HINT",   "Two words.")
        );

        rooms[3] = new SecurityOfficeRoom(
            env.get(p + "ROOM4_NAME",   "Security Office"),
            true,
            env.get(p + "ROOM4_PUZZLE", "What is the goal of every student?"),
            env.get(p + "ROOM4_ANSWER", "graduate"),
            env.get(p + "ROOM4_CLUE",   "The end of every student's journey."),
            env.get(p + "ROOM4_HINT",   "Starts with G.")
        );

        return rooms;
    }
}