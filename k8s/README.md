# Kubernetes manifests for gestion-users-roles

This directory contains basic Kubernetes manifests to deploy the `gestion-users-roles` Spring Boot application.

Files:
- `deployment.yaml` - Deployment with 2 replicas, resource requests/limits and health probes.
- `service.yaml` - ClusterIP service exposing port 80 -> container port 8080.
- `.gitkeep` - placeholder to keep the directory in git.

Notes / what to customize:
- Replace the `image` in `deployment.yaml` with your built image and tag (for example, a registry-hosted image).
- Adjust replica count, resource requests/limits and probe endpoints to match your application.
- If you use RBAC and a non-default service account, consider adding the `serviceAccountName` field and appropriate Role/RoleBinding manifests.
- Consider adding `ingress.yaml` or `horizontalpodautoscaler.yaml` if needed.
