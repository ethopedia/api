with constants (query) as (
    values (
               phraseto_tsquery(:searchText)
           )
)

select * from (
                  select
                      tld.cursor as id,
                      tld.video_id,
                      v.video_name,
                      tld.episode_number,
                      ts_headline('english', transcription, constants.query) as matched_text,
                      start_time,
                      start_time - lag(start_time) over (partition by tld.episode_number order by tld.start_time) diff


                  from
                      transcription_lookup_data tld

                          inner join videos v on v.video_id = tld.video_id
                     , constants

                  where tsvector @@ constants.query and tld.cursor > :offset

                  order by tld.cursor
              ) sub

where diff isnull or diff > 60

limit :limit;