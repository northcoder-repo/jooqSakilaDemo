package org.northcoder.sakila;

// WARNING - you have to explicitly import the following - see below
// import org.jooq.Record;
// WARNING - this is to avoid a conflict with Java 14's Record
import java.math.BigDecimal;
import java.text.Collator;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Cursor;
import org.jooq.DSLContext;
import org.jooq.DeleteQuery;
import org.jooq.Field;
import org.jooq.Query;
import org.jooq.Record; // must explicitly include this import
import org.jooq.Record1;
import org.jooq.Records;
import org.jooq.Result;
import org.jooq.ResultQuery;
import org.jooq.Row;
import org.jooq.Select;
import org.jooq.SelectQuery;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.conf.RenderImplicitJoinType;
import org.jooq.impl.DSL;
import static org.northcoder.sakila.jooq.Keys.*;
import static org.northcoder.sakila.jooq.Tables.*;
import org.northcoder.sakila.jooq.tables.*;
import org.northcoder.sakila.jooq.tables.pojos.*;
import org.northcoder.sakila.jooq.tables.records.*;
import org.northcoder.sakila.model.*;

class JooqExamples {

    private final DSLContext dsl;

    public JooqExamples(DSLContext dsl) {
        this.dsl = dsl;
    }

    public void fetchingExactlyOneRecord() {
        //
        // See also:
        // https://blog.jooq.org/the-many-different-ways-to-fetch-data-in-jooq/
        //
        // All jOOQ fetching transparently handles connections and prepared
        // statements - unless you go out of your way to change this.
        //
        // These will throw one of:
        //  - org.jooq.exception.TooManyRowsException
        //  - org.jooq.exception.NoDataFoundException
        // if the query does not return exactly 1 record.
        //
        // a single jOOQ Record from a specific table:
        //
        FilmRecord filmA = dsl.selectFrom(FILM).fetchAny();
        // can be used like a JavaBean:
        filmA.getDescription();
        filmA.setDescription("foo");

        // if you know there is only one matching record:
        FilmRecord filmB = dsl
                .selectFrom(FILM)
                .where(FILM.FILM_ID.eq(1))
                .fetchOne();

        // or the following (& lots of method signature variants)
        // useful when you can guarantee there will be exactly 1 record (e.g.
        // you are not relying on user-provided data).
        FilmRecord filmC = dsl.fetchSingle(FILM, FILM.FILM_ID.eq(1));

        // ------------------------------------------------------------------
        //
        // more examples, based on the article linked to above:
        //
        // my starting query:
        //
        ResultQuery<Record> query = dsl
                .select()
                .from(FILM)
                .where(FILM.FILM_ID.eq(1));

        // NULLABLE
        // null if no record found;
        // TooManyRowsException if more than 1 record found:
        // useful if you are relying on user-provided data (e.g. in a URL) to
        // find one record.
        FilmRecord filmD = query.fetchOne().into(FilmRecord.class);

        // OPTIONAL
        Optional<Record> filmE = query.fetchOptional();

    }

    public void fetchingZeroOneOrManyRecords() {
        //
        // collections of records: Result<FooRecord> or Result<Record>
        //
        Result<FilmRecord> filmsA = dsl.fetch(FILM);
        // the above is the same as the following...
        List<FilmRecord> filmsB = dsl.fetch(FILM);
        // ...but with a lot of additional jOOQ convenience methods.

        //
        // Consider the difference between select() and selectFrom()
        //
        // with the standard SQL select, you can use joins, etc, but you therefore
        // cannot specify a specific table-based result record (e.g. you can't
        // use Result<CountryRecord> here):
        //
        Result<Record> recA = dsl
                .select()
                .from(COUNTRY)
                .where(COUNTRY.COUNTRY_ID.le(7))
                .fetch();
        //
        // with selectFrom() you cannot add a join, but you do get a specific
        // result type - Result<CountryRecord> instead of Result<Record>
        //
        Result<CountryRecord> recB = dsl
                .selectFrom(COUNTRY)
                .where(COUNTRY.COUNTRY_ID.le(7))
                .fetch();

        // this uses an explicit fetch():
        Result<Record> filmsC = dsl.select().from(FILM).fetch();
        // but this uses an implicit .fetch():
        for (Record film : dsl.select().from(FILM)) {
            System.out.println(film.get(FILM.TITLE));
        }
        // or for more specific typing:
        for (FilmRecord film : dsl.selectFrom(FILM)) {
            System.out.println(film.get(FILM.TITLE));
        }

        // define now, but don't fetch yet:
        var sql = dsl
                .selectFrom(COUNTRY)
                .where(COUNTRY.COUNTRY_ID.le(7));
        // ok - fetch now:
        String resC = sql.fetch().getFirst().getCountry(); // Afghanistan

        // split a result record into separate records:
        Record cityCountryRec = dsl.select()
                .from(CITY)
                .join(COUNTRY)
                .on(CITY.COUNTRY_ID.eq(COUNTRY.COUNTRY_ID))
                .where(CITY.CITY_ID.eq(364))
                .fetchOne();
        // split the result record:
        CityRecord cityRec = cityCountryRec.into(CITY);
        CountryRecord countryRec = cityCountryRec.into(COUNTRY);

        // into a map: country ID to country
        Map<Integer, CountryRecord> countryMap = dsl.selectFrom(COUNTRY)
                .fetch().intoMap(COUNTRY.COUNTRY_ID);

        // into a map: country ID to list of cities
        Map<Integer, Result<CityRecord>> countryCitiesA = dsl.selectFrom(CITY)
                .fetch().intoGroups(CITY.COUNTRY_ID);
        // and then traverse the results:
        countryCitiesA.entrySet().forEach(e -> {
            System.out.println(e.getKey());
            e.getValue().forEach(v -> {
                System.out.println(" - " + v.getCity());
            });
        });

        // jOOQ school:
        Result<Record1<String>> filmsP = dsl
                .select(FILM.TITLE)
                .from(FILM)
                .where(FILM.RELEASE_YEAR.eq(Year.of(2006)))
                .fetch();

        // old school:
        String filmSql = """
                select title
                from film
                where release_year = ?
                """;
        Result<Record> filmsQ = dsl.fetch(filmSql, "2006");

        // ---------------------------------------------------------------------
        //
        // fetchMany() - for when the SQL returns multiple result sets:
        // (I have never written such SQL & not all DBs support this, anyway)
        //
        //Results many = dsl.fetchMany(someSqlStatement);
        //
        //
        // ---------------------------------------------------------------------
        //
        // not sure why/when I would use this:
        Table<FilmRecord> tfr = dsl.selectFrom(FILM).asTable();

        // need a closer look...
        Cursor<FilmRecord> cfr = dsl.selectFrom(FILM).fetchLazy();
    }

    public void basicProjections() {
        //
        // one of the places where Java 10's var keyword comes in handy
        //
        // In this example, var is actually Result<Record2<String,String>>
        //
        // Record2 is a strongly-typed 2-field record. jOOQ supports
        // Record1 through Record22 - and (untyped) RecordN beyond 22 fields.
        //
        var cityCountry = dsl
                .select(COUNTRY.COUNTRY_, CITY.CITY_)
                .from(COUNTRY)
                .innerJoin(CITY)
                .on(CITY.COUNTRY_ID.eq(COUNTRY.COUNTRY_ID))
                .where(COUNTRY.COUNTRY_ID.le(7))
                .orderBy(COUNTRY.COUNTRY_, CITY.CITY_)
                .fetch();

        // accessing the values in a result...
        // assume the following query:
        Record filmInfo = dsl
                .select(
                        FILM.FILM_ID,
                        FILM.DESCRIPTION.as("descr"))
                .from(FILM)
                .where(FILM.FILM_ID.eq(1))
                .fetchOne();
        // using Record, instead of FilmRecord there are no longer specific
        // getters and setters. You need to provide the field type in some cases:
        Integer i = filmInfo.get(FILM.FILM_ID);
        String s = filmInfo.get("descr", String.class);
        Integer j = filmInfo.get(0, Integer.class); // zero-based indexing
    }

    public void parentChildNavigation() {

        // one-to-many (FK): get all the cities for South Africa:
        CountryRecord country = dsl.fetchOne(COUNTRY, COUNTRY.COUNTRY_.eq("South Africa"));
        Result<CityRecord> cities = country.fetchChildren(FK_CITY_COUNTRY);
        cities.forEach(c -> System.out.println(c.getCity()));

        // one-to-one (reverse FK): get the country for Barcelona:
        CityRecord city = dsl.fetchOne(CITY, CITY.CITY_.eq("Barcelona"));
        CountryRecord country2 = city.fetchParent(FK_CITY_COUNTRY);
        // In this DB, Barcelona is the one in Venezuela, not the one in Spain:
        System.out.println(country2.getCountry());

        // many-to-many: get all the actors in the given film:
        // navigates the FILM_ACTOR resolution table:
        FilmRecord film = dsl.fetchOne(FILM, FILM.TITLE.eq("UNFORGIVEN ZOOLANDER"));
        // WARNING - this contains 2 separate fetches and therefore generates
        // 2 separate queries - not the most efficient approach:
        Result<ActorRecord> actors = film.fetchChildren(FK_FILM_ACTOR_FILM)
                .fetchParents(FK_FILM_ACTOR_ACTOR);
        actors.forEach(a -> System.out.println(a.getFirstName() + " " + a.getLastName()));
    }

    public void fetchingIntoPojos() {
        //
        // fetch...into... a jooq-generated pojo
        // (I have a naming directive in the code generator which adds
        // "Pojo" to the Java record name - hence "FilmPojo" generated by jOOQ)
        //
        Film film = dsl.fetchSingle(FILM, FILM.FILM_ID.eq(924))
                .into(Film.class);

        List<Film> films1 = dsl.fetch(FILM).into(Film.class);

        List<Film> films2 = dsl.select()
                .from(FILM)
                .where(FILM.DESCRIPTION.containsIgnoreCase("fateful"))
                .orderBy(FILM.TITLE)
                .limit(10)
                .fetchInto(Film.class);
    }

    public void pathJoining() {
        //
        // https://www.jooq.org/doc/3.19/manual/sql-building/sql-statements/select-statement/explicit-path-join/
        //
        // Basic example: CITY.country() - from CITY to COUNTRY
        //
        // provided by the code generator based on FK relationship:
        // CITY.country().COUNTRY_ (from city table to country table)
        //
        var cityCountry = dsl.select(
                CITY.CITY_,
                CITY.country().COUNTRY_)
                .from(CITY)
                .where(CITY.country().COUNTRY_.eq("Malaysia"))
                .fetch();
        cityCountry.forEach(cc -> System.out.println(
                cc.get(CITY.CITY_)
                + ", " + cc.get(COUNTRY.COUNTRY_)
                + ", " + cc.get(CITY.country().COUNTRY_)
        ));

        //
        // explicit path joins
        //
        // Path joins are created implicitly
        var result1 = dsl.select(
                STORE.staff().FIRST_NAME,
                STORE.staff().LAST_NAME,
                STORE.address().city().CITY_)
                .from(STORE)
                .fetch();

        // Path joins are created explicitly (e.g. using table lists)
        // (another way to get result1):
        var result2 = dsl.select(
                STORE.staff().FIRST_NAME,
                STORE.staff().LAST_NAME,
                STORE.address().city().CITY_)
                .from(STORE, STORE.staff(), STORE.address().city())
                .fetch();

        // Path joins are created explicitly (e.g. using inner joins, with optional ON clause)
        // (yet another way to get result1):
        var result3 = dsl.select(
                STORE.staff().FIRST_NAME,
                STORE.staff().LAST_NAME,
                STORE.address().city().CITY_)
                .from(STORE)
                .join(STORE.staff())
                .join(STORE.address().city())
                .fetch();

        //
        // implicit to-many path joins
        //
        //  https://www.jooq.org/doc/3.19/manual/sql-building/sql-statements/select-statement/implicit-to-many-join/
        //
        //
        // this works with an explicit left join using a path join
        var result4 = dsl.select(
                ACTOR.FIRST_NAME,
                ACTOR.LAST_NAME,
                DSL.count(ACTOR.film().FILM_ID))
                .from(ACTOR)
                .leftJoin(ACTOR.film()) // need this line!
                .groupBy(ACTOR.ACTOR_ID)
                .fetch();
        //
        //FAIL: Implicit to-many JOIN of `sakila`.`actor` isn't supported with Settings.renderImplicitJoinToManyType = DEFAULT
        /*  var result4 = dsl.select(
                  ACTOR.FIRST_NAME,
                  ACTOR.LAST_NAME,
                  DSL.count(ACTOR.film().FILM_ID))
                  .from(ACTOR)
                  .groupBy(ACTOR.ACTOR_ID)
                  .fetch();
         */
        // another example - this one with a cartesian product (and the
        // use of an explicit join)
        var result5 = dsl.select(
                ACTOR.FIRST_NAME,
                ACTOR.LAST_NAME)
                .from(ACTOR)
                .leftJoin(ACTOR.film()) // yes, we skipped over the relationship table (yay codegen!)
                .where(ACTOR.film().FILM_ID.isNotNull())
                .fetch();

        // without the above explicit left join, we have an implicit to-many join
        // and that throws a jOOQ error. See the linked page above for why.
        // FAIL:
        /*  var result6 = dsl.select(
                  ACTOR.FIRST_NAME,
                  ACTOR.LAST_NAME)
                  .from(ACTOR)
                  //.leftJoin(ACTOR.film())
                  .where(ACTOR.film().FILM_ID.isNotNull())
                  .fetch();
         */
        //
        //
        // https://blog.jooq.org/jooq-3-19s-new-explicit-and-implicit-to-many-path-joins/
        //
        // example result:
        // |PENELOPE  |GUINESS     |[(ACADEMY DINOSAUR), (ANACONDA CONFESSIONS), (A...|[(Documentary), (Animation), (New), (Games), (S...|
        // |NICK      |WAHLBERG    |[(ADAPTATION HOLES), (APACHE DIVINE), (BABY HAL...|[(Documentary), (Family), (Foreign), (Action), ...|
        //
        var result7 = dsl.select(
                ACTOR.FIRST_NAME,
                ACTOR.LAST_NAME,
                DSL.multiset(DSL.select(ACTOR.film().TITLE).from(ACTOR.film())).as("films"),
                DSL.multiset(
                        DSL.selectDistinct(ACTOR.film().category().NAME)
                                .from(ACTOR.film().category())
                ).as("categories")
        )
                .from(ACTOR)
                .fetch();

        //
        // changing the default path join behavior
        //
        // As shown above, by default, implicit to-many JOINs will fail
        // see Settings.withRenderImplicitJoinToManyType
        // https://www.jooq.org/doc/latest/manual/sql-building/dsl-context/custom-settings/settings-implicit-join-type/
        dsl.settings()
                .withRenderImplicitJoinType(RenderImplicitJoinType.INNER_JOIN)
                .withRenderImplicitJoinToManyType(RenderImplicitJoinType.LEFT_JOIN);

        // --- DEFAULT FAIL ---
        // by default, this would fail without the above settings changes:
        var filmActors = dsl.select(
                FILM.TITLE.as("title"),
                FILM.actor().FIRST_NAME.as("firstName"),
                FILM.actor().LAST_NAME.as("lastName"))
                .from(FILM)
                .where(FILM.FILM_ID.eq(924))
                .fetch();
        filmActors.forEach(f -> System.out.println(
                f.get("title") + ": " + f.get("firstName") + " " + f.get("lastName")));

        // reverse the temporary settings changes we made above:
        dsl.settings()
                .withRenderImplicitJoinType(RenderImplicitJoinType.DEFAULT)
                .withRenderImplicitJoinToManyType(RenderImplicitJoinType.DEFAULT);

    }

    public void multisets() {
        //
        // this is awesome
        //
        // https://blog.jooq.org/jooq-3-15s-new-multiset-operator-will-change-how-you-think-about-sql/
        //
        // ---------------------------------------------------------------------
        //
        // FIRST EXAMPLE - NO MULTISETS
        //
        // no multiset - just denormalization, same as some of the earlier examples
        // var is: Result<Record4<String,String,String,String>>
        // this is quite wasteful in terms of repeated/redundant data in the results
        //
        var resultOne = dsl.select(
                FILM.TITLE,
                ACTOR.FIRST_NAME,
                ACTOR.LAST_NAME,
                CATEGORY.NAME
        )
                .from(ACTOR)
                .join(FILM_ACTOR)
                .on(ACTOR.ACTOR_ID.eq(FILM_ACTOR.ACTOR_ID))
                .join(FILM)
                .on(FILM_ACTOR.FILM_ID.eq(FILM.FILM_ID))
                .join(FILM_CATEGORY)
                .on(FILM.FILM_ID.eq(FILM_CATEGORY.FILM_ID))
                .join(CATEGORY)
                .on(FILM_CATEGORY.CATEGORY_ID.eq(CATEGORY.CATEGORY_ID))
                .orderBy(1, 2, 3, 4)
                .fetch();

        //
        // SECOND EXAMPLE - WITH MULTISETS
        //
        // with multiset - nested results, for example:
        // |ACADEMY DINOSAUR           |[(PENELOPE, GUINESS), (CHRISTIAN, GABLE), (LUCI...|[(Documentary)]|
        // that's why there are 3 Result objects in our var:
        // var is: Result<Record3<String,Result<Record2<String,String>>,Result<Record1<String>>>>
        //
        var resultTwo
                = dsl.select(
                        FILM.TITLE, // <--- component 1
                        DSL.multiset( // <--- component 2
                                DSL.select(
                                        FILM_ACTOR.actor().FIRST_NAME,
                                        FILM_ACTOR.actor().LAST_NAME)
                                        .from(FILM_ACTOR)
                                        .where(FILM_ACTOR.FILM_ID.eq(FILM.FILM_ID))
                        ).as("actors"),
                        DSL.multiset( // <--- component 3
                                DSL.select(FILM_CATEGORY.category().NAME)
                                        .from(FILM_CATEGORY)
                                        .where(FILM_CATEGORY.FILM_ID.eq(FILM.FILM_ID))
                        ).as("categories")
                )
                        .from(FILM)
                        .where(FILM.TITLE.startsWithIgnoreCase("arm"))
                        .orderBy(FILM.TITLE)
                        .fetch();

        System.out.println(resultTwo.formatJSON());
        //
        //Result:
        //  String    <- component1() - title
        //  Result:   <- component2()
        //    String                  - first name
        //    String                  - last name
        //  Result:   <- component3()
        //    String                  - category
        //
        // navigating through the results:
        resultTwo.forEach(ttl -> {
            // print the title:
            System.out.println(ttl.get(FILM.TITLE));
            // print the category:
            ttl.component3().forEach(cat -> {
                System.out.println(" - cat: " + cat.component1());
            });
            // print the actors:
            ttl.component2().forEach(act -> {
                //System.out.println(" - act: " + act.get(0) + " " + act.get(1));
                System.out.println(" - act: " + act.component1() + " " + act.component2());
            });
        });

    }

    public void nestedJavaRecords() {
        //
        // Here, I first created 3 simple Java records to hold the data:
        //  - FilmRec
        //  - ActorRec
        //  - CategoryRec
        // I map the earlier "resultTwo" multiset to my custom record FilmRec, which
        // contains additional custom records ActorRec & CategoryRec:
        //
        // (Also contains some path joins - see below for more about those).
        //
        // EXAMPLE 1: mapped to my Java records:
        //
        List<FilmRec> resultOne = dsl.select(
                FILM.TITLE,
                DSL.multiset(
                        DSL.select(
                                FILM_ACTOR.actor().FIRST_NAME,
                                FILM_ACTOR.actor().LAST_NAME)
                                .from(FILM_ACTOR)
                                .where(FILM_ACTOR.FILM_ID.eq(FILM.FILM_ID))
                ).as("actors").convertFrom(r -> r.map(Records.mapping(ActorRec::new))),
                DSL.multiset(
                        DSL.select(FILM_CATEGORY.category().NAME)
                                .from(FILM_CATEGORY)
                                .where(FILM_CATEGORY.FILM_ID.eq(FILM.FILM_ID))
                ).as("categories").convertFrom(r -> r.map(Records.mapping(CategoryRec::new)))
        )
                .from(FILM)
                .where(FILM.TITLE.startsWithIgnoreCase("arm"))
                .orderBy(FILM.TITLE)
                .fetch(Records.mapping(FilmRec::new));

        //
        // EXAMPLE 2:
        //
        // map the previous result to my custom record FilmRec2, which
        // contains ActorRec (same as previous example), but which uses
        // List<String> instead of List<CategoryRec> - which you can see at
        // r.map(Record1::value1) - the jOOQ Record1 class and method value1()
        // so this is a hybrid, using 2 of my own Java records and one jOOQ
        // record (Record1):
        //
        List<FilmRec2> resultTwo = dsl.select(
                FILM.TITLE,
                DSL.multiset(
                        DSL.select(
                                FILM_ACTOR.actor().FIRST_NAME,
                                FILM_ACTOR.actor().LAST_NAME)
                                .from(FILM_ACTOR)
                                .where(FILM_ACTOR.FILM_ID.eq(FILM.FILM_ID))
                ).convertFrom(r -> r.map(Records.mapping(ActorRec::new)))
                        .as("actors"),
                DSL.multiset(
                        DSL.select(FILM_CATEGORY.category().NAME)
                                .from(FILM_CATEGORY)
                                .where(FILM_CATEGORY.FILM_ID.eq(FILM.FILM_ID))
                ).convertFrom(r -> r.map(Record1::value1))
                        .as("categories")
        )
                .from(FILM)
                .where(FILM.TITLE.startsWithIgnoreCase("arm"))
                .orderBy(FILM.TITLE)
                .fetch(Records.mapping(FilmRec2::new));

        //
        // Related example (but no multisets here):
        //
        // embedding FK records in records: CityRec contains CountryRec
        // using DSL.row()
        //
        List<CityRec> resultThree = dsl.select(
                CITY.CITY_,
                DSL.row(
                        CITY.country().COUNTRY_
                ).mapping(CountryRec::new).as("country"))
                .from(CITY)
                .where(CITY.CITY_.startsWithIgnoreCase("LO"))
                .fetch(Records.mapping(CityRec::new));
    }

    public void updating() {
        //
        // if you use optimistic locking TIMESTAMP or VERSION fields, then these
        // values are auto-updated by jOOQ and automatically provided in the
        // inserted/updated record.
        // https://www.jooq.org/doc/latest/manual/sql-execution/crud-with-updatablerecords/optimistic-locking/
        //
        // the config we are using:
        Configuration cfg = dsl.configuration();

        // fetch a record:
        FilmRecord film = dsl.selectFrom(FILM).fetchAny();
        // record is attached to the config used by dsl:
        Configuration cfgA = film.configuration();
        film.detach(); // or film.attach() which means "attach to nothing"
        // this is now null:
        cfgA = film.configuration();

        // same applies to a collection of records:
        Result<FilmRecord> films = dsl.selectFrom(FILM).fetch();
        Configuration cfgB = films.configuration();
        films.detach();
        cfgB = films.configuration();

        //
        // update a record:
        ///
        boolean changed = false;

        // no changes have been made to the record's data:
        changed = film.changed(); // false

        Year year = film.getReleaseYear(); // 2006
        film.setReleaseYear(Year.of(2006)); // updating to the same value

        changed = film.changed(); // true (even though it's 2006 -> 2006)

        // FAIL - because the record is not attached to the config, because we
        // detached it ourselves, earlier (we wouldn't normally do this)
        // "...the return value of "org.jooq.impl.TableRecordImpl.configuration()" is null"
        //int i = film.update(); // this FAILS
        //
        int count = 0; // count how many rows are changed by insert/update
        // re-attach our config to the record:
        film.attach(dsl.configuration());
        //
        // SQL for the following update():
        /*
           update `sakila`.`film`
           set `sakila`.`film`.`release_year` = ?, `sakila`.`film`.`last_update` = ?
           where (`sakila`.`film`.`film_id` = ?
           and `sakila`.`film`.`last_update` = ?)

           note the extra WHERE clause: `and `sakila`.`film`.`last_update` = ?`
           the 4 params are:
            - 2006
            - {ts '2024-08-27 11:56:45.0'}
            - 1
            - {ts '2006-02-15 05:03:42.0'}
           this is because of .withExecuteWithOptimisticLocking(true)
           as configured in our settings (see main() method)
         */
        count = film.update(); // count = 1

        changed = film.changed(); // false because no new changes after update()
        //
        // no SQL is generated because of .withInsertUnchangedRecords(false)
        // as configured in our settings (see main() method):
        count = film.update(); // count = 0

    }

    public void insertingAndDeleting() {
        //
        // the notes in the "updating" section mostly apply here, also.
        //
        // insert a record
        //
        // create a new record:
        CountryRecord country = new CountryRecord(
                110,
                "Nowhereistan",
                LocalDateTime.now()
        );

        // attach it to a config:
        country.attach(dsl.configuration());
        //
        // insert it:
        int insertCount = country.insert(); // count = 1

        // retrieve the record again (just for fun):
        country = dsl.fetchSingle(COUNTRY, COUNTRY.COUNTRY_.eq("Nowhereistan"));

        // delete it:
        int deleteCount = dsl.delete(COUNTRY)
                .where(COUNTRY.COUNTRY_ID.eq(country.getCountryId()))
                .execute();  // count = 1
        //
        // or another way to delete:
        //
        DeleteQuery<CountryRecord> deleteQuery = dsl.deleteQuery(COUNTRY);
        deleteQuery.addConditions(COUNTRY.COUNTRY_ID.eq(country.getCountryId()));
        int deleteCount2 = deleteQuery.execute();
        // this also lets you do the following... but the result may be null,
        // depending on your DB's capabilities:
        CountryRecord deletedRecord = deleteQuery.getReturnedRecord();
    }

    public void pathCorrelation() {
        // see https://blog.jooq.org/to-dao-or-not-to-dao/
        //
        // consider this (DAOs were used in the original example, but not here):

        // Executes 585 queries ("N + 1" or, rather, "1 + 584"):
        //
        // THIS IS BAD! DON'T DO THIS!
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (CustomerRecord customer : dsl
                .selectFrom(CUSTOMER)
                .where(CUSTOMER.ACTIVE.eq(Byte.decode("1")))) {
            for (PaymentRecord payment : dsl
                    .selectFrom(PAYMENT)
                    .where(PAYMENT.CUSTOMER_ID
                            .eq(customer.getCustomerId()))) {
                totalAmount = totalAmount.add(payment.getAmount());
            }
        }
        System.out.println(totalAmount); // 65754.56

        // a correlated "where exists" using a path which provides the implicit
        // join
        // only one query!
        //
        // THIS IS BETTER! DO THIS INSTEAD! (even if you are not using jOOQ)
        totalAmount = BigDecimal.ZERO;
        for (PaymentRecord payment : dsl
                .selectFrom(PAYMENT)
                .where(DSL.exists(DSL.selectOne()
                        .from(PAYMENT.customer())
                        .where(PAYMENT.customer().ACTIVE.eq(Byte.decode("1")))
                )).fetchInto(PaymentRecord.class)) {
            totalAmount = totalAmount.add(payment.getAmount());
        }
        System.out.println(totalAmount); // 65754.56

        // or a slightly more verbose version, using explicit join:
        totalAmount = BigDecimal.ZERO;
        for (PaymentRecord payment : dsl
                .selectFrom(PAYMENT)
                .where(DSL.exists(DSL.selectOne()
                        .from(CUSTOMER)
                        .where(PAYMENT.CUSTOMER_ID.eq(CUSTOMER.CUSTOMER_ID))
                        .and(CUSTOMER.ACTIVE.eq(Byte.decode("1")))
                )).fetchInto(PaymentRecord.class)) {
            totalAmount = totalAmount.add(payment.getAmount());
        }
        System.out.println(totalAmount); // 65754.56

        // can we optimize the projection, as well?
        // the following generates this SQL:
        /*
           select `sakila`.`payment`.`amount`
           from `sakila`.`payment`
           where exists (
             select 1 as `one`
             from `sakila`.`customer`
             where (`sakila`.`payment`.`customer_id` = `sakila`.`customer`.`customer_id`
             and `sakila`.`customer`.`active` = ?)
           )
         */
        totalAmount = BigDecimal.ZERO;
        for (BigDecimal amount : dsl
                .select(PAYMENT.AMOUNT)
                .from(PAYMENT)
                .where(DSL.exists(DSL.selectOne()
                        .from(CUSTOMER)
                        .where(PAYMENT.CUSTOMER_ID.eq(CUSTOMER.CUSTOMER_ID))
                        .and(CUSTOMER.ACTIVE.eq(Byte.decode("1")))
                )).fetchInto(BigDecimal.class)) {
            totalAmount = totalAmount.add(amount);
        }
        System.out.println(totalAmount); // 65754.56

    }

    public void aliasing() {
        //
        // no aliasing:
        //
        List<FilmRec2> resultOne = dsl.select(
                FILM.TITLE,
                DSL.multiset(
                        DSL.select(
                                FILM_ACTOR.actor().FIRST_NAME,
                                FILM_ACTOR.actor().LAST_NAME)
                                .from(FILM_ACTOR)
                                .where(FILM_ACTOR.FILM_ID.eq(FILM.FILM_ID))
                ).convertFrom(r -> r.map(Records.mapping(ActorRec::new)))
                        .as("actors"),
                DSL.multiset(
                        DSL.select(FILM_CATEGORY.category().NAME)
                                .from(FILM_CATEGORY)
                                .where(FILM_CATEGORY.FILM_ID.eq(FILM.FILM_ID))
                ).convertFrom(r -> r.map(Record1::value1))
                        .as("categories")
        )
                .from(FILM)
                .where(FILM.TITLE.startsWithIgnoreCase("arm"))
                .orderBy(FILM.TITLE)
                .fetch(Records.mapping(FilmRec2::new));

        //
        // with table aliasing:
        //
        FilmTable f = FILM.as("f");
        FilmActorTable fa = FILM_ACTOR.as("fa");
        FilmCategoryTable fc = FILM_CATEGORY.as("fc");

        List<FilmRec2> resultTwo = dsl.select(
                f.TITLE,
                DSL.multiset(
                        DSL.select(
                                fa.actor().FIRST_NAME,
                                fa.actor().LAST_NAME)
                                .from(fa)
                                .where(fa.FILM_ID.eq(f.FILM_ID))
                ).convertFrom(r -> r.map(Records.mapping(ActorRec::new)))
                        .as("actors"),
                DSL.multiset(
                        DSL.select(fc.category().NAME)
                                .from(fc)
                                .where(fc.FILM_ID.eq(f.FILM_ID))
                ).convertFrom(r -> r.map(Record1::value1))
                        .as("categories")
        )
                .from(f)
                .where(f.TITLE.startsWithIgnoreCase("arm"))
                .orderBy(f.TITLE)
                .fetch(Records.mapping(FilmRec2::new));

    }

    public void commonJooqTypes() {
        //
        // https://blog.jooq.org/a-brief-overview-over-the-most-common-jooq-types/
        //
        // these all extend org.jooq.QueryPart - the common base type of
        // the entire jOOQ expression tree:
        //
        Table qp1 = FILM;
        Field qp2 = FILM.FILM_ID;
        Field qp3 = ACTOR.FIRST_NAME;
        TableField<FilmRecord, Integer> qp4 = FILM.FILM_ID;
        Condition qp5 = FILM.FILM_ID.eq(1);
        Row qp6 = DSL.row(qp2, qp3);
        Condition qp7 = DSL.row(ACTOR.FIRST_NAME, ACTOR.LAST_NAME).eq("John", "Smith");
        // Not sure about the practical differences...
        Select qp8 = dsl.select().from(FILM);
        ResultQuery qp9 = dsl.select().from(FILM);
        Query qp10 = dsl.select().from(FILM);

        // you can use query parts to build queries incrementally - for example:
        SelectQuery<Record> query = dsl.selectQuery();
        query.addFrom(CITY);
        if (true) {
            query.addJoin(COUNTRY, COUNTRY.COUNTRY_ID.eq(CITY.COUNTRY_ID));
        }
        Result<?> result = query.fetch();
    }

    public void dao() {
        //
        Dao dao = new Dao(dsl);
        //
        // get all:
        //
        List<FilmRecord> filmsA = dao.fetchAll(FILM, FilmRecord.class);
        System.out.println(filmsA.getFirst().getTitle());
        //
        // with sorting (this example adapts one of my earlier POJO queries):
        //
        Select filmRecQuery = DSL.select(
                FILM.TITLE,
                DSL.multiset(
                        DSL.select(
                                FILM_ACTOR.actor().FIRST_NAME,
                                FILM_ACTOR.actor().LAST_NAME)
                                .from(FILM_ACTOR)
                                .where(FILM_ACTOR.FILM_ID.eq(FILM.FILM_ID))
                ).as("actors").convertFrom(r -> r.map(Records.mapping(ActorRec::new))),
                DSL.multiset(
                        DSL.select(FILM_CATEGORY.category().NAME)
                                .from(FILM_CATEGORY)
                                .where(FILM_CATEGORY.FILM_ID.eq(FILM.FILM_ID))
                ).as("categories").convertFrom(r -> r.map(Records.mapping(CategoryRec::new)))
        )
                .from(FILM)
                .orderBy(FILM.TITLE.asc());
        List<FilmRec> filmRecs = dao.fetchPojoList(filmRecQuery, FilmRec.class);

        //
        // Sorting with a Java comparator and a locale:
        //
        final Collator french = Collator.getInstance(Locale.forLanguageTag("fr-FR"));
        final Comparator<ActorRecord> actorComparator = Comparator
                .comparing(ActorRecord::getLastName, french)
                .thenComparing(ActorRecord::getFirstName, french);
        List<ActorRecord> recs = dao.fetchAllSorted(ACTOR, ActorRecord.class, actorComparator);

        //
        // get one:
        //
        ResultQuery<Record> query = dsl
                .select()
                .from(FILM)
                .where(FILM.FILM_ID.eq(12));
        FilmRecord filmA = dao.fetchOne(query, FilmRecord.class);
        System.out.println(filmA.getTitle());
        //
        // create:
        //
        // changed() is false - so no insert would be attempted
        // see withInsertUnchangedRecords(false)
        filmA.changed(); // false
        filmA.setDescription("FOO BAR");
        filmA.changed(); // true (so an insert will now be be attempted)
        DaoResult resultOne = dao.insert(filmA); // this INSERT will fail
        System.out.println(resultOne.ex().getCause().getMessage());
        //
        // update:
        //
        filmA.changed(); // still true
        DaoResult resultTwo = dao.update(filmA); // this UPDATE will succeed
        //
        // delete:
        //
        //Query deleteQueryA = dsl.delete(COUNTRY)
        //        .where(COUNTRY.COUNTRY_ID.eq(110));
        DaoResult resultThree = dao.delete(COUNTRY, COUNTRY.COUNTRY_ID.eq(110));
        // or:
        //DeleteQuery<FilmRecord> deleteQueryB = dsl.deleteQuery(FILM);
        //deleteQueryB.addConditions(FILM.FILM_ID.eq(12));
        DaoResult resultFour = dao.delete(FILM, FILM.FILM_ID.eq(12));
        System.out.println(resultFour.ex().getCause().getMessage());
    }

    public void plainSql() {
        //
        // or you can just use your plain SQL strings:
        // https://www.jooq.org/doc/latest/manual/sql-building/plain-sql/
        // https://www.jooq.org/doc/latest/manual/sql-execution/query-vs-resultquery/
        //
        String sql = """
                     select title
                     from film
                     where film_id = ?
                     or release_year > ?
                     """;
        ResultQuery<Record> resultQuery = dsl.resultQuery(sql, 999, "1984");
        Result<Record> result = resultQuery.fetch();
        //
        Query query = dsl.query("delete from country where country_id = ?", 999);
        int count = query.execute();
    }

}
