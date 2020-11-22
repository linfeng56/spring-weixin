package ${package.Mapper};

import ${package.Entity}.${entity};
import ${superMapperClassPackage};

import org.springframework.stereotype.Repository;

/**
 * ${table.comment!} Mapper 接口.
 *
 * @author ${author}
 */
@Repository
<#if kotlin>
interface ${table.mapperName} : ${superMapperClass}<${entity}>
<#else>
public interface ${table.mapperName} extends ${superMapperClass}<${entity}> {

}
</#if>
