package edu.spingsecurity.service;

import edu.spingsecurity.dto.CompanyDto;
import edu.spingsecurity.dto.converters.CompanyDtoConverter;
import edu.spingsecurity.repository.CompanyRepository;
import edu.spingsecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    private final CompanyDtoConverter companyDtoConverter;

    //@PreFilter(value = "hasPermission(filterObject,'READ')",filterTarget = "companies")
    //@PostFilter("hasPermission(filterObject, 'READ')")
    //@PreAuthorize("hasPermission('ADMIN')")
    @Transactional(readOnly = true)
    public List<CompanyDto> getAll() {
        return companyRepository.findAll().stream()
            .map(companyDtoConverter::toDto)
            .collect(toList());
    }

    //@Secured("ROLE_ADMIN")
    @Transactional(readOnly = true)
    public List<CompanyDto> getAllByUserId(long userId) {
        var user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User does not exist"));
        return user.getCompanies().stream()
            .map(companyDtoConverter::toDto)
            .collect(toList());
    }

    //@Secured("ROLE_ADMIN")
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    //@PreAuthorize("hasRole('ADMIN')")
    @PostAuthorize("hasAuthority('ROLE_ADMIN')")
    @Transactional(readOnly = true)
    public CompanyDto getById(long companyId) {
        return companyRepository.findById(companyId)
            .map(companyDtoConverter::toDto)
            .orElseThrow(() -> new IllegalArgumentException("Company does not exist"));
    }


    //@PreAuthorize("hasPermission(#company,'WRITE')")
    @Transactional
    public CompanyDto createCompany(CompanyDto newCompany, long userId) {
        var user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User does not exist"));
        var company = companyDtoConverter.toDomain(newCompany);
        var withId = companyRepository.save(company);
        user.getCompanies().add(company);
        userRepository.save(user);
        return companyDtoConverter.toDto(withId);
    }

    @Transactional
    public CompanyDto updateCompany(CompanyDto companyDto) {
        var company = companyRepository.findById(companyDto.getId())
            .orElseThrow(() -> new IllegalArgumentException("Company does not exist"));
        var updated = companyDtoConverter.toDomain(companyDto, company);
        var fromDb = companyRepository.save(updated);
        return companyDtoConverter.toDto(fromDb);
    }
}
