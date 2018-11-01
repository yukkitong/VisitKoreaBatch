package kr.co.uniess.vk.batch.repository

import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

@Mapper
interface TestMapper {

    @Select("""
       SELECT COUNT(*) FROM IMAGE         
    """)
    int countImages()
}