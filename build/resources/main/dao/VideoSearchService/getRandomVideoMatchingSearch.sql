with constants (query) as (
    values (
       phraseto_tsquery(:searchText)
    )
)

select
    tld.id,
    tld.cursor,
    tld.video_id,
    v.video_name,
    v.episode_number,
    (v.video_url || '?start=' || start_time) as video_url,
    ts_headline('english', transcription, constants.query) as matched_text,
    start_time,
    start_time - lag(start_time) over (partition by tld.episode_number order by start_time) diff


from
    transcription_lookup_data tld

        inner join videos v on v.video_id = tld.video_id
   , constants

where tsvector @@ constants.query

order by random() limit 1;