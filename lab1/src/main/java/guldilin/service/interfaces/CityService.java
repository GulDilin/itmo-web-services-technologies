package guldilin.service.interfaces;

import guldilin.dto.CityDTO;
import guldilin.dto.FilterArgumentDTO;
import guldilin.dto.PaginationDTO;
import guldilin.dto.PaginationInfoDTO;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import java.util.List;

@WebService
public interface CityService {
    @WebMethod
    public PaginationDTO<CityDTO> findByFilter(List<FilterArgumentDTO> filters, PaginationInfoDTO pagination);
}
