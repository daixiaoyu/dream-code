-- 学号 课程  程

   1     2   60
   1     3   50
   1     4   70

-- 可以选多门课程
   所选所有课程都及格的学生

select
  user_id
FROM (

         SELECT
             user_id,
             (count(课程) - sum(IF(score >= 60,1,0)) as goodCount ) AS `cast`
         FROM TALBE
         GROUP  BY user_id
) a
where a.`cast` = 0








