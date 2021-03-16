package com.itkee.admin.entity.dto;


import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author rabbit
 */
@Data
@Builder
public class MenuCheckDTO {

    List<MenuButtonDTO> menus;

    List<Integer> defaultCheckKeys;
}
