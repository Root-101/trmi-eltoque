--
-- PostgreSQL database dump
--

-- Dumped from database version 15.4 (Debian 15.4-1.pgdg120+1)
-- Dumped by pg_dump version 15.3

-- Started on 2023-09-23 00:10:02

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 215 (class 1259 OID 16825)
-- Name: trmi; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.trmi (
    trmi_id integer NOT NULL,
    eur numeric(6,3),
    usd numeric(6,3),
    mlc numeric(6,3),
    register_date timestamp without time zone NOT NULL,
    created_at timestamp without time zone NOT NULL
);


ALTER TABLE public.trmi OWNER TO postgres;

--
-- TOC entry 214 (class 1259 OID 16824)
-- Name: trmi_trmi_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.trmi_trmi_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.trmi_trmi_id_seq OWNER TO postgres;

--
-- TOC entry 3353 (class 0 OID 0)
-- Dependencies: 214
-- Name: trmi_trmi_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.trmi_trmi_id_seq OWNED BY public.trmi.trmi_id;


--
-- TOC entry 3199 (class 2604 OID 16828)
-- Name: trmi trmi_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.trmi ALTER COLUMN trmi_id SET DEFAULT nextval('public.trmi_trmi_id_seq'::regclass);


--
-- TOC entry 3201 (class 2606 OID 16834)
-- Name: trmi UNIQUE_register_date; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.trmi
    ADD CONSTRAINT "UNIQUE_register_date" UNIQUE (register_date);


--
-- TOC entry 3203 (class 2606 OID 16830)
-- Name: trmi trmi_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.trmi
    ADD CONSTRAINT trmi_pkey PRIMARY KEY (trmi_id);


-- Completed on 2023-09-23 00:10:03

--
-- PostgreSQL database dump complete
--

