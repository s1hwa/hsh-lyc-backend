package euclid.lyc_spring.service.point;

import euclid.lyc_spring.dto.response.PointResDTO;

public interface PointService {

    PointResDTO.MemberPointDTO getMyPoints();

    PointResDTO.UsageListDTO getMyPointUsages();
}
