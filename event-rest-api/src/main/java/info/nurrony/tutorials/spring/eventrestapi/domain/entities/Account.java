package info.nurrony.tutorials.spring.eventrestapi.domain.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Builder
@Table(name = "accounts")
@ToString(exclude = { "posts" })
@EqualsAndHashCode(exclude = { "posts" })
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "is required")
    private String password;

    @NotNull(message = "is required")
    private String userName;

    @NotNull(message = "is required")
    private String nickname;

    @NotNull(message = "is required")
    private Date createDate;

    @NotNull(message = "is required")
    private Integer state;

    @JsonIgnoreProperties({ "account" })
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Post> posts;

    public void addPost(Post post) {
        posts.add(post);
        post.setAccount(this);
    }

    public void removePost(Post post) {
        posts.remove(post);
        post.setAccount(null);
    }

}
