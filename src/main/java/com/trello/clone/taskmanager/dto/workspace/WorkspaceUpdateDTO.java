package com.trello.clone.taskmanager.dto.workspace;

import com.trello.clone.taskmanager.model.WorkspaceVisibility;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceUpdateDTO {

    @Size(min = 3, max = 100, message = "Workspace name must be between 3 and 100 characters")
    private String name;

    private WorkspaceVisibility visibility;

    private String backgroundImgUrl;
}