package org.test.bo.utility;

import java.util.List;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.test.core.utility.ObjectMapperProvider;

@UtilityClass
@Slf4j
public class ObjectTypeUtils {

  public static boolean areAllElementsBelongToClass(List<?> list, Class<?> clazz) {
    boolean allValid = true;
    for (Object element : list) {
      if (!isElementBelongToClass(element, clazz)) {
        log.error("Element failed deserialization to {}: {}", clazz.getSimpleName(), element);
        allValid = false;
      }
    }
    return allValid;
  }

  public static boolean isElementBelongToClass(Object obj, Class<?> clazz) {
    try {
      ObjectMapperProvider.getInstance()
                          .convertValue(obj, clazz);
      return true;
    } catch (Exception e) {
      log.debug("Failed to deserialize object: {}", obj, e);
      return false;
    }
  }
}
