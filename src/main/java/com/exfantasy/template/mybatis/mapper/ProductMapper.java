package com.exfantasy.template.mybatis.mapper;

import com.exfantasy.template.mybatis.model.Product;
import com.exfantasy.template.mybatis.model.ProductExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

@Mapper
public interface ProductMapper {
    @SelectProvider(type=ProductSqlProvider.class, method="countByExample")
    int countByExample(ProductExample example);

    @DeleteProvider(type=ProductSqlProvider.class, method="deleteByExample")
    int deleteByExample(ProductExample example);

    @Delete({
        "delete from product",
        "where prod_id = #{prodId,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer prodId);

    @Insert({
        "insert into product (prod_name)",
        "values (#{prodName,jdbcType=VARCHAR})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="prodId", before=false, resultType=Integer.class)
    int insert(Product record);

    @InsertProvider(type=ProductSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="prodId", before=false, resultType=Integer.class)
    int insertSelective(Product record);

    @SelectProvider(type=ProductSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="prod_id", property="prodId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="prod_name", property="prodName", jdbcType=JdbcType.VARCHAR)
    })
    List<Product> selectByExample(ProductExample example);

    @Select({
        "select",
        "prod_id, prod_name",
        "from product",
        "where prod_id = #{prodId,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="prod_id", property="prodId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="prod_name", property="prodName", jdbcType=JdbcType.VARCHAR)
    })
    Product selectByPrimaryKey(Integer prodId);

    @UpdateProvider(type=ProductSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") Product record, @Param("example") ProductExample example);

    @UpdateProvider(type=ProductSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") Product record, @Param("example") ProductExample example);

    @UpdateProvider(type=ProductSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Product record);

    @Update({
        "update product",
        "set prod_name = #{prodName,jdbcType=VARCHAR}",
        "where prod_id = #{prodId,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Product record);
}