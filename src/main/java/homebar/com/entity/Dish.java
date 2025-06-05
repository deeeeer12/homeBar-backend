package homebar.com.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("dishes")
public class Dish {
    private Integer id;
    private String name;
    private String category; //种类
    private String imageUrl;
    private String description;
    private Integer type; // 1，经典 2，Jay 3,JJ 4，陶喆 5，网红
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updatedAt;

    //逻辑删除
    @TableLogic
    private Integer isDeleted;
    // getters and setters
}

