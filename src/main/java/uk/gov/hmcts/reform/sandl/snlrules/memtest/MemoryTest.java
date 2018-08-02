package uk.gov.hmcts.reform.sandl.snlrules.memtest;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.UUID;

import uk.gov.hmcts.reform.sandl.snlrules.model.Availability;
import uk.gov.hmcts.reform.sandl.snlrules.model.HearingPart;
import uk.gov.hmcts.reform.sandl.snlrules.model.Judge;
import uk.gov.hmcts.reform.sandl.snlrules.model.Room;
import uk.gov.hmcts.reform.sandl.snlrules.model.Session;
import uk.gov.hmcts.reform.sandl.snlrules.services.DroolsService;

public class MemoryTest
{
	public final static int JUDGE_TYPE = 0;
	public final static int ROOM_TYPE = 1;
	public final static int AVAILABILITY_TYPE = 2;
	public final static int SESSION_TYPE = 3;
	public final static int HEARING_PART_TYPE = 4;

	public static int JUDGE_COUNT = 50000;
	public final static Judge[] JUDGES = new Judge[JUDGE_COUNT];

	public static int ROOM_COUNT = 5000;
	public final static Room[] ROOMS = new Room[ROOM_COUNT];

	public final static int PERIOD_DAYS = 365;

	public static final OffsetDateTime AVAILABILITY_START = OffsetDateTime.parse("2018-09-01T09:00:00+00:00");
	public static final int AVAILABILITY_DURATION = 60 * 8;

	public static final int SESSION_DURATION = 60 * 3;

	public static final double SESSION_PROBABILITY = 0.98;
	public static int SESSION_COUNT;
	public static String[] CASE_TYPES = new String[] {"CASE_TYPE_0", "CASE_TYPE_1", "CASE_TYPE_2", "CASE_TYPE_3", "CASE_TYPE_4", "CASE_TYPE_5", "CASE_TYPE_6", "CASE_TYPE_7", "CASE_TYPE_8", "CASE_TYPE_9"};

	public static final int HEARING_DURATION = 30;
	public static final int SESSION_HEARING_COUNT = 6;
	public static final double HEARING_PROBABILITY = 0.98;
	public static final double RANDOM_HEARING_PROBABILITY = 0.03;

	public final DroolsService drools;
	public int nextHearingId = 0;

	public MemoryTest(DroolsService drools)
	{
		this.drools = drools;
	}

	public void createAll()
	{
		createJudges();
		createJudgesAvailability();
		createRooms();
		createRoomsAvailability();
		SESSION_COUNT = createSessions();
	}

	public void report()
	{
		System.out.println("ADDED " + JUDGES_ADDED + " judges.");
		System.out.println("ADDED " + ROOMS_ADDED + " rooms.");
		System.out.println("ADDED " + AVAILABILITY_ADDED + " availabilities.");
		System.out.println("ADDED " + SESSIONS_ADDED + " sessions.");
		System.out.println("ADDED " + HEARING_PARTS_ADDED + " hearing parts.");
	}

	public void createJudges()
	{
		for (int i = 0; i < JUDGE_COUNT; ++i)
		{
			JUDGES[i] = new Judge(id(JUDGE_TYPE, i), "Judge_" + i);
			addJudge(JUDGES[i]);
		}
	}

	public void createRooms()
	{
		for (int i = 0; i < ROOM_COUNT; ++i)
		{
			ROOMS[i] = new Room(id(ROOM_TYPE, i), "Room_" + i);
			addRoom(ROOMS[i]);
		}
	}

	public void createJudgesAvailability()
	{
		int id = 0;
		OffsetDateTime start = AVAILABILITY_START;
		for (int i = 0; i < PERIOD_DAYS; ++i)
		{
			if (start.getDayOfWeek() != DayOfWeek.SATURDAY && start.getDayOfWeek() != DayOfWeek.SUNDAY)
			{
				for (int j = 0; j < JUDGE_COUNT; ++j)
				{
					Duration duration = Duration.ofMinutes(AVAILABILITY_DURATION);
					Availability a = new Availability(id(AVAILABILITY_TYPE, id++), JUDGES[j].getId(), null, start, duration);
					addAvailability(a);
				}
			}
			start = start.plusDays(1L);
		}
	}

	public void createRoomsAvailability()
	{
		int id = 0;
		OffsetDateTime start = AVAILABILITY_START;
		for (int i = 0; i < PERIOD_DAYS; ++i)
		{
			if (start.getDayOfWeek() != DayOfWeek.SATURDAY && start.getDayOfWeek() != DayOfWeek.SUNDAY)
			{
				for (int j = 0; j < ROOM_COUNT; ++j)
				{
					Duration duration = Duration.ofMinutes(AVAILABILITY_DURATION);
					Availability a = new Availability(id(AVAILABILITY_TYPE, id++), null, ROOMS[j].getId(), start, duration);
					addAvailability(a);
				}
			}
			start = start.plusDays(1L);
		}
	}

	public int createSessions()
	{
		int nextId = 0;
		for (Room room : ROOMS)
		{
			nextId = createSessionsForRoom(room.getId(), nextId);
		}
		return nextId;
	}

	public int createSessionsForRoom(String roomId, int nextId)
	{
		OffsetDateTime start = AVAILABILITY_START;
		for (int i = 0; i < PERIOD_DAYS; ++i)
		{
			if (start.getDayOfWeek() != DayOfWeek.SATURDAY && start.getDayOfWeek() != DayOfWeek.SUNDAY)
			{
				for (int j = 0; j < ROOM_COUNT; ++j)
				{
					if (Math.random() < SESSION_PROBABILITY)
					{
						String judgeId = JUDGES[(int)(Math.random() * JUDGE_COUNT)].getId();
						Duration duration = Duration.ofMinutes(SESSION_DURATION);
						Session s = new Session(id(SESSION_TYPE, nextId++), judgeId, ROOMS[j].getId(), start, duration, CASE_TYPES[nextId % CASE_TYPES.length]);
						addSession(s);
						createHearingsForSession(s);
					}
				}
			}
			start = start.plusDays(1L);
		}
		return nextId;
	}

	public void createHearingsForSession(Session s)
	{
		for (int i = 0; i < SESSION_HEARING_COUNT; ++i)
		{
			if (Math.random() < HEARING_PROBABILITY)
			{
				Duration duration = Duration.ofMinutes(HEARING_DURATION);
			    //public HearingPart(String id, String sessionId, String caseType, Duration duration, OffsetDateTime createdAt) {
				OffsetDateTime start = s.getStart().minusDays(15);
				OffsetDateTime end = s.getStart().plusDays(15);
				OffsetDateTime created = s.getStart().minusDays(30);
				HearingPart h = new HearingPart(
						id(HEARING_PART_TYPE, nextHearingId++),
						s.getId(),
						s.getCaseType(),
						duration,
						start,
						end,
						created);
				addHearingPart(h);
			}
		}
		if (Math.random() < RANDOM_HEARING_PROBABILITY)
		{
			Duration duration = Duration.ofMinutes((int)(Math.random() * 3 * HEARING_DURATION));
		    //public HearingPart(String id, String sessionId, String caseType, Duration duration, OffsetDateTime createdAt) {
			OffsetDateTime start = s.getStart().minusDays((int)(Math.random() * 100) - 50);
			OffsetDateTime end = start.plusDays(30);
			OffsetDateTime created = start.minusDays(30);
			HearingPart h = new HearingPart(
					id(HEARING_PART_TYPE, nextHearingId++),
					s.getId(),
					CASE_TYPES[(int)(Math.random() * CASE_TYPES.length)],
					duration,
					start,
					end,
					created);
			addHearingPart(h);
		}
	}

	public String id(int type, int index)
	{
		return new UUID(type, index).toString();
	}

	public int JUDGES_ADDED = 0;
	public int ROOMS_ADDED = 0;
	public int AVAILABILITY_ADDED;
	public int SESSIONS_ADDED = 0;
	public int HEARING_PARTS_ADDED = 0;

	public void addJudge(Judge j)
	{
		addFact(j);
		++JUDGES_ADDED;
	}

	public void addRoom(Room r)
	{
		addFact(r);
		++ROOMS_ADDED;
	}

	public void addAvailability(Availability a)
	{
		addFact(a);
		++AVAILABILITY_ADDED;
	}

	public void addSession(Session s)
	{
		addFact(s);
		++SESSIONS_ADDED;
	}

	public void addHearingPart(HearingPart h)
	{
		addFact(h);
		++HEARING_PARTS_ADDED;
	}

	public void addFact(Object f)
	{
		if (drools != null)
		{
			drools.getRulesSession().insert(f);
		}
	}

	public void run()
	{
		System.out.println("Creating domain entities.");
		createAll();
		System.out.println("Firing all rules.");
		if (drools != null)
		{
			drools.getRulesSession().fireAllRules();
		}
		System.out.println("Finished.");
		report();
	}

	public static void main(String[] args)
	{
		new MemoryTest(null).run();
	}
}
