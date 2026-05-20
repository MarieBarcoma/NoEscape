package noescape;

class ClassroomRoom extends Room {

    public ClassroomRoom(String name, boolean locked, String puzzle, String answer, String clue,   String hint) {
        super(name, locked, puzzle, answer, clue, hint);
    }

    @Override
    public String getRoomType() {
        return "Classroom";
    }
}

// Room 2: Library
class LibraryRoom extends Room {

    public LibraryRoom(String name, boolean locked, String puzzle, String answer, String clue,   String hint) {
        super(name, locked, puzzle, answer, clue, hint);
    }

    @Override
    public String getRoomType() {
        return "Library";
    }
}
// Room 3: TSG - Technology Support Group
class TsgRoom extends Room {

    public TsgRoom(String name, boolean locked, String puzzle, String answer, String clue,   String hint) {
        super(name, locked, puzzle, answer, clue, hint);
    }

    @Override
    public String getRoomType() {
        return "TSG";
    }
}

// Room 4: Security Office (final room)
class SecurityOfficeRoom extends Room {

    public SecurityOfficeRoom(String name, boolean locked, String puzzle, String answer, String clue,   String hint) {
        super(name, locked, puzzle, answer, clue, hint);
    }

    @Override
    public String getRoomType() {
        return "Security Office";
    }
}

class RoomFactory {

    public static Room[] createRooms(EnvLoader env, String course) {

        String p = course.contains("Nursing") ? "NR_" : "CS_";

        Room[] rooms = new Room[4];

        rooms[0] = new ClassroomRoom(
            env.get(p + "ROOM1_NAME", "Classroom 101"),
            false,
            env.get(p + "ROOM1_PUZZLE", "What is your field of study?"),
            env.get(p + "ROOM1_ANSWER", "unknown"),
            env.get(p + "ROOM1_CLUE", "Think about your course."),
            env.get(p + "ROOM1_HINT", "Check your ID card.")
        );

        rooms[1] = new LibraryRoom(
            env.get(p + "ROOM2_NAME", "Library"),
            true,
            env.get(p + "ROOM2_PUZZLE", "What do you find on every shelf here?"),
            env.get(p + "ROOM2_ANSWER", "book"),
            env.get(p + "ROOM2_CLUE", "Look around you."),
            env.get(p + "ROOM2_HINT", "Starts with B.")
        );

        rooms[2] = new TsgRoom(
            env.get(p + "ROOM3_NAME", "TSG"),
            true,
            env.get(p + "ROOM3_PUZZLE", "What does TSG help students with?"),
            env.get(p + "ROOM3_ANSWER", "tech support"),
            env.get(p + "ROOM3_CLUE", "TSG = Technology Support Group."),
            env.get(p + "ROOM3_HINT", "Two words.")
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