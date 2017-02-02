package com.exfantasy.template.mybatis.mapper;

import com.exfantasy.template.mybatis.model.MailTemplate;
import com.exfantasy.template.mybatis.model.MailTemplateExample;
import com.exfantasy.template.mybatis.model.MailTemplateWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

@Mapper
public interface MailTemplateMapper {
    @Delete({
        "delete from mail_template",
        "where template_id = #{templateId,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer templateId);

    @Insert({
        "insert into mail_template (template_id, type, ",
        "subject, header, ",
        "body_header, body_tail, ",
        "tail)",
        "values (#{templateId,jdbcType=INTEGER}, #{type,jdbcType=VARCHAR}, ",
        "#{subject,jdbcType=VARCHAR}, #{header,jdbcType=LONGVARCHAR}, ",
        "#{bodyHeader,jdbcType=LONGVARCHAR}, #{bodyTail,jdbcType=LONGVARCHAR}, ",
        "#{tail,jdbcType=LONGVARCHAR})"
    })
    int insert(MailTemplateWithBLOBs record);

    @InsertProvider(type=MailTemplateSqlProvider.class, method="insertSelective")
    int insertSelective(MailTemplateWithBLOBs record);

    @SelectProvider(type=MailTemplateSqlProvider.class, method="selectByExampleWithBLOBs")
    @Results({
        @Result(column="template_id", property="templateId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="type", property="type", jdbcType=JdbcType.VARCHAR),
        @Result(column="subject", property="subject", jdbcType=JdbcType.VARCHAR),
        @Result(column="header", property="header", jdbcType=JdbcType.LONGVARCHAR),
        @Result(column="body_header", property="bodyHeader", jdbcType=JdbcType.LONGVARCHAR),
        @Result(column="body_tail", property="bodyTail", jdbcType=JdbcType.LONGVARCHAR),
        @Result(column="tail", property="tail", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<MailTemplateWithBLOBs> selectByExampleWithBLOBs(MailTemplateExample example);

    @SelectProvider(type=MailTemplateSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="template_id", property="templateId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="type", property="type", jdbcType=JdbcType.VARCHAR),
        @Result(column="subject", property="subject", jdbcType=JdbcType.VARCHAR)
    })
    List<MailTemplate> selectByExample(MailTemplateExample example);

    @Select({
        "select",
        "template_id, type, subject, header, body_header, body_tail, tail",
        "from mail_template",
        "where template_id = #{templateId,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="template_id", property="templateId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="type", property="type", jdbcType=JdbcType.VARCHAR),
        @Result(column="subject", property="subject", jdbcType=JdbcType.VARCHAR),
        @Result(column="header", property="header", jdbcType=JdbcType.LONGVARCHAR),
        @Result(column="body_header", property="bodyHeader", jdbcType=JdbcType.LONGVARCHAR),
        @Result(column="body_tail", property="bodyTail", jdbcType=JdbcType.LONGVARCHAR),
        @Result(column="tail", property="tail", jdbcType=JdbcType.LONGVARCHAR)
    })
    MailTemplateWithBLOBs selectByPrimaryKey(Integer templateId);

    @UpdateProvider(type=MailTemplateSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(MailTemplateWithBLOBs record);

    @Update({
        "update mail_template",
        "set type = #{type,jdbcType=VARCHAR},",
          "subject = #{subject,jdbcType=VARCHAR},",
          "header = #{header,jdbcType=LONGVARCHAR},",
          "body_header = #{bodyHeader,jdbcType=LONGVARCHAR},",
          "body_tail = #{bodyTail,jdbcType=LONGVARCHAR},",
          "tail = #{tail,jdbcType=LONGVARCHAR}",
        "where template_id = #{templateId,jdbcType=INTEGER}"
    })
    int updateByPrimaryKeyWithBLOBs(MailTemplateWithBLOBs record);

    @Update({
        "update mail_template",
        "set type = #{type,jdbcType=VARCHAR},",
          "subject = #{subject,jdbcType=VARCHAR}",
        "where template_id = #{templateId,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(MailTemplate record);
}