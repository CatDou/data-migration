DROP FUNCTION IF EXISTS public.add_1;

CREATE FUNCTION public.add_1(a integer, b integer) RETURNS integer
    LANGUAGE sql
    AS $_$
    SELECT $1+$2;
$_$;


ALTER FUNCTION public.add_1(a integer, b integer) OWNER TO postgres;

--
-- Name: pgsql_demo(numeric); Type: FUNCTION; Schema: public; Owner: postgres
--

DROP FUNCTION IF EXISTS public.pgsql_demo_1;

CREATE FUNCTION public.pgsql_demo_1(total numeric) RETURNS numeric
    LANGUAGE plpgsql
    AS $$
BEGIN
    RETURN total * 0.8;
END;
$$;


ALTER FUNCTION public.pgsql_demo_1(total numeric) OWNER TO postgres;

--
-- Name: test_fun(); Type: FUNCTION; Schema: public; Owner: postgres
--

DROP FUNCTION IF EXISTS public.test_fun_1;

CREATE FUNCTION public.test_fun_1() RETURNS integer
    LANGUAGE plpgsql
    AS $$
declare total integer;
	BEGIN
		select count(1) into total from t_user;
		return total;
	END;
$$;


ALTER FUNCTION public.test_fun_1() OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: t_user; Type: TABLE; Schema: public; Owner: postgres
--

DROP TABLE IF EXISTS public.t_user_2;

CREATE TABLE public.t_user_2 (
    id integer,
    name character varying(30),
    address character varying(100)
);


ALTER TABLE public.t_user_2 OWNER TO postgres;

--
-- Name: t_user_1; Type: TABLE; Schema: public; Owner: postgres
--

DROP TABLE IF EXISTS public.t_user_3;

CREATE TABLE public.t_user_3 (
    id integer,
    name character varying(30),
    address character varying(100)
);


ALTER TABLE public.t_user_3 OWNER TO postgres;

--
-- PostgreSQL database dump complete
--

