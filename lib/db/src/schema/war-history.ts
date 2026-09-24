import { boolean, integer, jsonb, pgTable, serial, text, timestamp, unique } from "drizzle-orm/pg-core";

export const warHistoryTable = pgTable(
  "war_history",
  {
    id: serial("id").primaryKey(),
    warKey: text("war_key").notNull(),
    clanTag: text("clan_tag").notNull(),
    endTime: text("end_time"),
    state: text("state"),
    result: text("result"),
    clanName: text("clan_name"),
    opponentName: text("opponent_name"),
    clanStars: integer("clan_stars"),
    opponentStars: integer("opponent_stars"),
    clanDestruction: integer("clan_destruction"),
    opponentDestruction: integer("opponent_destruction"),
    teamSize: integer("team_size"),
    attacksPerMember: integer("attacks_per_member"),
    rawWar: jsonb("raw_war"),
    capturedAt: timestamp("captured_at", { withTimezone: true }).notNull().defaultNow(),
  },
  (table) => ({
    warKeyUnique: unique("war_history_war_key_unique").on(table.warKey),
  }),
);

export const warPlayerAttacksTable = pgTable(
  "war_player_attacks",
  {
    id: serial("id").primaryKey(),
    warKey: text("war_key").notNull(),
    playerTag: text("player_tag").notNull(),
    playerName: text("player_name"),
    townHallLevel: integer("town_hall_level"),
    attackIndex: integer("attack_index").notNull(),
    stars: integer("stars"),
    destruction: integer("destruction"),
    targetMapPosition: integer("target_map_position"),
    defenderTag: text("defender_tag"),
    defenderName: text("defender_name"),
    attackTime: text("attack_time"),
    capturedAt: timestamp("captured_at", { withTimezone: true }).notNull().defaultNow(),
  },
  (table) => ({
    attackUnique: unique("war_player_attack_unique").on(
      table.warKey,
      table.playerTag,
      table.attackIndex,
    ),
  }),
);

export type WarHistory = typeof warHistoryTable.$inferSelect;
export type WarPlayerAttack = typeof warPlayerAttacksTable.$inferSelect;
