package com.trello.clone.taskmanager.dto.workspace;

import com.trello.clone.taskmanager.model.WorkspaceVisibility; // Asigură-te că importul e corect
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceCreationDTO {

    @NotBlank(message = "Workspace name cannot be empty")
    @Size(min = 3, max = 100, message = "Workspace name must be between 3 and 100 characters")
    private String name;

    @NotNull(message = "Visibility cannot be null")
    private WorkspaceVisibility visibility;

    @URL(message = "Background image URL should be valid")
    private String backgroundImgUrl;

}